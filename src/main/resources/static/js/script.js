async function login() {
    const email = document.querySelector("#email").value
    const password = document.querySelector("#password").value

    const response = await fetch("http://localhost:8080/auth/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            email: email,
            password: password
        })
    }
    ).catch(err => console.log(err))

    let p = document.querySelector("#popup")
    if (response.status === 200) {
        const responseJson = await response.json()
        const id = responseJson.id
        const allname = responseJson.name.split(" ")
        const username = allname[0]
        const role = responseJson.tech
        sessionStorage.setItem("id", id)
        sessionStorage.setItem("username", username)
        sessionStorage.setItem("role", role)
        p.innerHTML = "Login efetuado com sucesso!"
        setTimeout(() => {
            p.innerHTML = ''
            redirecionar("http://localhost:8080/html/main-page.html")
        }, 3000)
    } else if (response.status === 403) {
        p.innerHTML = "Senha Incorreta!"
    } else {
        p.innerHTML = "Usuário inexistente!"
    }
}

async function register() {
    const name = document.querySelector("#name").value
    const email = document.querySelector("#email").value
    const password = document.querySelector("#password").value
    const fradio = document.getElementsByName("role")
    let isTech = ''

    if (fradio[0].checked)
        isTech = false
    else
        isTech = true

    const response = await fetch("http://localhost:8080/auth/register", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            "name": name,
            "email": email,
            "password": password,
            "tech": isTech
        })
    }
    ).catch(err => console.log(err))

    let p = document.querySelector("#popup")
    if (response.status === 201) {
        p.innerHTML = "Cadastro efetuado com sucesso!"
        setTimeout(() => {
            p.innerHTML = ''
            redirecionar("http://localhost:8080/html/login.html")
        }, 3000)
    } else {
        p.innerHTML = "Usuário já existe!"
    }
}

function redirecionar(url) {
    window.location.href = url
}

function setUsername() {
    const username = sessionStorage.getItem("username")

    let userDiv = document.querySelector("div#user")
    userDiv.innerHTML += username
}

async function loadNoCompletedTickets() {
    fetch("http://localhost:8080/ticket/false", {

    })
        .then(response => response.json())
        .then(tickets => {
            const ticketList = document.getElementById("no-completed-ticket-list");
            tickets.forEach(ticket => {
                const ticketItem = document.createElement("div");
                if (sessionStorage.getItem("role")) {
                    if (ticket.techId == sessionStorage.getItem("id")) {
                        ticketItem.innerHTML = `
            <a href="chat.html?ticketId=${ticket.id}" class="ticket">
              Ticket #${ticket.id} - ${ticket.title}
            </a>
          `;
                        ticketList.appendChild(ticketItem);
                    }
                } else {
                    if (ticket.userId == sessionStorage.getItem("id")) {
                        ticketItem.innerHTML = `
        <a href="chat.html?ticketId=${ticket.id}" class="ticket">
          Ticket #${ticket.id} - ${ticket.title}
        </a>
      `;
                        ticketList.appendChild(ticketItem);
                    }
                }
            });
        })
        .catch(error => console.error("Erro ao carregar os tickets:", error));
}

async function loadTicketsWithNoTech() {
    if (sessionStorage.getItem("role")) {
        fetch("http://localhost:8080/ticket")
            .then(response => response.json())
            .then(tickets => {
                const ticketList = document.getElementById("ticket-list");
                ticketList.innerHTML += "<h1>Tickets Não Atendidos</h1>"
                tickets.forEach(ticket => {
                    const ticketItem = document.createElement("div");
                    ticketItem.innerHTML = `
            <h1 class="ticket" onclick="setTech(${ticket.id})">
              Ticket #${ticket.id} - ${ticket.title}
            </h1>
          `;
                    ticketList.appendChild(ticketItem);
                }
                );
            }
            )
            .catch(error => console.error("Erro ao carregar os tickets:", error));
    }
}

async function loadCompletedTickets() {
    fetch("http://localhost:8080/ticket/true", {

    })
        .then(response => response.json())
        .then(tickets => {
            const ticketList = document.getElementById("completed-ticket-list");
            tickets.forEach(ticket => {
                const ticketItem = document.createElement("div");
                if (sessionStorage.getItem("role")) {
                    if (ticket.techId == sessionStorage.getItem("id")) {
                        ticketItem.innerHTML = `
            <a href="chat.html?ticketId=${ticket.id}" class="ticket">
              Ticket #${ticket.id} - ${ticket.title}
            </a>
          `;
                        ticketList.appendChild(ticketItem);
                    }
                } else {
                    if (ticket.userId == sessionStorage.getItem("id")) {
                        ticketItem.innerHTML = `
        <a href="chat.html?ticketId=${ticket.id}" class="ticket">
          Ticket #${ticket.id} - ${ticket.title}
        </a>
      `;
                        ticketList.appendChild(ticketItem);
                    }
                }
            });
        })
        .catch(error => console.error("Erro ao carregar os tickets:", error));
}

async function setTech(ticketId) {
    response = await fetch(`http://localhost:8080/ticket/all/${ticketId}`)
        .then(response => response.json())
        .catch(err => console.log(err)
        )
    const id = sessionStorage.getItem("id")

    fetch(`http://localhost:8080/ticket/${id}`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            "id": response.id,
            "title": response.title,
            "userId": response.userId,
            "techId": id,
            "completed": response.completed
        })
    }
    ).catch(err => console.log(err))

    location.reload()
}
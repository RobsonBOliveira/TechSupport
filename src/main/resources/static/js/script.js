const socket = new WebSocket('ws://localhost:8080/conect');
const Client = Stomp.over(socket);

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
        }, 1500)
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
        }, 1500)
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

function createTicketButton() {
    const role = sessionStorage.getItem("role");

    if (role === "true") {
        return;
    }

    const botao = document.createElement("button");
    botao.innerText = "Criar Novo Ticket"
    botao.id = "create-ticket-btn"
    botao.onclick = "redirecionar(http://localhost:8080/html/create-ticket.html)"
    botao.addEventListener("click", () => {
        window.location.href = "http://localhost:8080/html/create-ticket.html";
    });

    document.getElementById("ticket-list").prepend(botao);
}

async function createTicket() {
    const title = document.querySelector("#title").value
    const userId = sessionStorage.getItem("id")
    fetch("http://localhost:8080/ticket", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            "title": title,
            "userId": userId,
            "completed": false
        })
    }).catch(err => log)

    setTimeout(() => {
        redirecionar("http://localhost:8080/html/main-page.html")
    }, 1500)
}

async function loadNoCompletedTickets() {
    fetch("http://localhost:8080/ticket/false", {

    })
        .then(response => response.json())
        .then(tickets => {
            const ticketList = document.getElementById("no-completed-ticket-list");
            tickets.forEach(ticket => {
                const ticketItem = document.createElement("div");
                if (sessionStorage.getItem("role") == "true") {
                    if (ticket.techId == sessionStorage.getItem("id")) {
                        ticketItem.innerHTML = `
            <h1 class="ticket">
              Ticket #${ticket.id} - ${ticket.title}
             - <button onclick="openChat(${ticket.id}, '${ticket.title}')">Entrar no Chat</button></h1>
          `;
                        ticketList.appendChild(ticketItem);
                    }
                } else {
                    if (ticket.userId == sessionStorage.getItem("id")) {
                        ticketItem.innerHTML = `
        <h1 class="ticket">
              Ticket #${ticket.id} - ${ticket.title}
             - <button onclick="openChat(${ticket.id}, '${ticket.name}')">Entrar no Chat</button></h1>
      `;
                        ticketList.appendChild(ticketItem);
                    }
                }
            });
        })
        .catch(error => console.error("Erro ao carregar os tickets:", error));
}

async function completeTickets() {
    const ticketId = sessionStorage.getItem("ticketId")
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
            "completed": true
        })
    }
    ).catch(err => console.log(err))

    redirecionar("http://localhost:8080/html/main-page.html")
}

async function loadTicketsWithNoTech() {
    if (sessionStorage.getItem("role") == "true") {
        fetch("http://localhost:8080/ticket")
            .then(response => response.json())
            .then(tickets => {
                const ticketList = document.getElementById("ticket-list");
                ticketList.innerHTML += "<h1>Tickets Não Atendidos</h1>"
                tickets.forEach(ticket => {
                    const ticketItem = document.createElement("div");
                    ticketItem.innerHTML = `
            <h1 class="ticket">
              Ticket #${ticket.id} - ${ticket.title}
             - <button onclick="setTech(${ticket.id})">Atender</button></h1>
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
            <h1 class="ticket">
              Ticket #${ticket.id} - ${ticket.title}
            </h1>
          `;
                        ticketList.appendChild(ticketItem);
                    }
                } else {
                    if (ticket.userId == sessionStorage.getItem("id")) {
                        ticketItem.innerHTML = `
        <h1 class="ticket">
              Ticket #${ticket.id} - ${ticket.title}
            </h1>
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

function openChat(id, name) {
    sessionStorage.setItem("ticketId", id)
    sessionStorage.setItem("header", name)
    redirecionar("http://localhost:8080/chat.html")
}

function setHeader() {
    const header = sessionStorage.getItem("header")
    const chatHeader = document.getElementById("chatHeader")
    chatHeader.innerHTML = `<h3>${header}</h3>`
}

function displayMessage(message, name) {
    const chatMessages = document.getElementById("chatMessages");
    const messageElement = document.createElement("div");

    messageElement.textContent = name + ": " + message;
    chatMessages.appendChild(messageElement);
}

function sendMessage(e) {
    e.preventDefault()
    const messageInput = document.getElementById("messageInput").value

    const message = {
        ticketId: sessionStorage.getItem("ticketId"),
        senderName: sessionStorage.getItem("username"),
        content: messageInput
    }

    Client.send("/app/chatmessage", {}, JSON.stringify(message))
    document.getElementById("messageInput").value = ""
}

function conect() {
    Client.connect({}, function (frame) {
        console.log(frame);

        Client.subscribe("/chat", function (message) {
            const chatMessage = JSON.parse(message.body)
            displayMessage(chatMessage.content, chatMessage.senderName)
        })
    })
}
conect()
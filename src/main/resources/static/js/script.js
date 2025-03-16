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
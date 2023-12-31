const alertAdd = document.getElementById("alertAdd");
const alertDelete = document.getElementById("alertDelete");
const form = document.getElementById('form');
const email = document.getElementById('email');
const name = document.getElementById('name');
const date = document.getElementById('date');
const cpf = document.getElementById('cpf');
const degree = document.getElementById('degree');
let id;
let cpfMask;
let cpfUnmasked;
let maxYear;
let minYear;
let validEmail = false;
let validDate = false;
let validCpf = false;
let validName = false;

/**
 * Função que ao inicializar o DOM coloca máscara no CPF e max e min do input Data.
 * */
document.addEventListener("DOMContentLoaded", function () {
    const maskOptions = {
        mask: '000.000.000-00'
    };

    cpfMask = IMask(cpf, maskOptions);

    const thisYear = new Date().getFullYear();
    minYear = thisYear - 25;
    maxYear = thisYear - 70;

    date.setAttribute("max", minYear + "-12-31")
    date.setAttribute("min", maxYear + "-01-01")
});

/**
 * Coloca um evento em cada botão de atualizar, para pegar o id do professor
 * */
document.querySelectorAll(".updateProfessor").forEach(
    function (button) {
        button.addEventListener("click", function () {
            id = button.closest("tr").querySelector(".id").value;
            alertAdd.style.display = 'none';
            getProfessor(id);
        });
    });

/**
 * Coloca um evento em cada botão de deletar, para pegar o id do professor
 * */
document.querySelectorAll(".deleteProfessor").forEach(
    function (button) {
        button.addEventListener("click", function () {
            id = button.closest("tr").querySelector(".id").value;
            document.getElementById("deleteButton").disabled = false;
            alertDelete.style.display = 'none';
        });
    });

/**
 * Coloca um evento no botão salvar, para pegar o id do professor.
 * */
form.addEventListener('submit', e => {
    console.log(id)
    e.preventDefault();
    alertAdd.style.display = 'none';
    validate();
    if (id > 0) {
        if (validCpf && validName && validEmail && validDate) {
            putProfessor(id);
        }
    } else if (validCpf && validName && validEmail && validDate) {
        postProfessor();
    }
});

/**
 * Função para adicionar professor.
 * */
function postProfessor() {

    let formData = {
        name: name.value,
        email: email.value,
        cpf: cpfUnmasked,
        degree: degree.value,
        date: date.value
    };

    axios({
        url: '/admin/professor/add',
        method: 'POST',
        data: JSON.stringify(formData),
        headers: {
            'Content-Type': 'application/json',
        }
    })
        .then(function (response) {
            setMessageSuccess("Professor adicionado!!", "add")
        })
        .catch(function (error) {
            if (error.response.status === 409) {
                const validate = error.response.data;
                duplicateValues(validate);
            } else {
                setMessageError("Não foi possível adicionar o Professor!!");
            }
        });
}

/**
 * Função para pegar Professor.
 * */
function getProfessor(id) {
    axios.get(`/admin/professor/update/${id}`)
        .then(function (response) {
            const data = response.data;
            name.value = data.name;
            cpfMask.value = data.cpf;
            email.value = data.email;
            degree.value = data.degree;
            date.value = data.date;
        })
        .catch(function (error) {
            if (error.response.status === 404) {
                setMessageError("Não foi possível buscar o Professor!!");
            }
        });
}

/**
 * Função para atualizar Professor.
 * */
function putProfessor(id) {
    let formData = {
        name: name.value,
        email: email.value,
        cpf: cpfUnmasked,
        degree: degree.value,
        date: date.value
    };
    axios({
        method: 'PUT',
        url: `/admin/professor/update/${id}`,
        data: formData,
        headers: {
            'Content-Type': 'application/json',
        }
    })
        .then(function (response) {
            setMessageSuccess("Professor atualizado!!", "update")
        })
        .catch(function (error) {
            if (error.response.status === 409) {
                const validate = error.response.data;
                console.log(validate);
                duplicateValues(validate);
            }
            if (error.response.status === 404) {
                setMessageError("Não foi possível atualizar o Professor!!");
            }
        });
}

/**
 * Função para deletar Professor.
 * */
function deleteProfessor() {
    axios({
        method: 'delete',
        url: `/admin/professor/delete/${id}`,
    })
        .then(function (response) {
            location.reload();
        })
        .catch(function (error) {
            if (error.response.status === 409) {
                alertDelete.style.display = 'block';
                document.getElementById("deleteButton").disabled = true;
                id = 0
            }
        });
}

/**
 * Função para validar nome, cpf, email e data de nascimento do professor.
 * */
function validate() {
    cpfUnmasked = cpfMask.unmaskedValue;
    const emailValue = email.value;
    const nameValue = name.value;
    const dateValue = new Date(date.value).getFullYear();

    let regexName = new RegExp("[A-Za-zÀ-ÖØ-öø-ÿ\\s\\-']{3,}");
    let regexEmail = new RegExp('[a-zA-Z0-9.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{3}(\\.[a-zA-Z]{2})?');

    if (!regexName.test(nameValue)) {
        setError(name, "Nome não é válido!");
        validName = false;
    } else {
        setSuccess(name);
        validName = true;
    }

    if (!regexEmail.test(emailValue)) {
        setError(email, "Email não é válido!");
        validEmail = false;
    } else {
        setSuccess(email);
        validEmail = true;
    }
    if (/^(\d)\1{10}/.test(cpfUnmasked) || !validateDigits(cpfUnmasked) || cpfUnmasked.length !== 11) {
        setError(cpf, "CPF não é válido!");
        validCpf = false;
    } else {
        setSuccess(cpf);
        validCpf = true;
    }

    if (dateValue <= minYear || dateValue >= maxYear) {
        setSuccess(date);
        validDate = true;
    }
}

/**
 * Função para validar digitos do cpf
 * */
function validateDigits(cpf) {
    let sum = 0;
    for (let i = 0; i < 9; i++) {
        sum += cpf.charAt(i) * (10 - i);
    }

    let rest = sum % 11;

    let firstDigit = 11 - rest;

    if (rest === 1 || rest === 0) firstDigit = 0;

    sum = 0;

    for (let i = 0; i < 10; i++) {
        sum += cpf.charAt(i) * (11 - i);
    }

    rest = sum % 11;
    let secondDigit = 11 - rest;
    if (rest === 1 || rest === 0) {
        secondDigit = 0;
    }

    let digits = firstDigit + "" + secondDigit;
    let cpfDigits = cpf.charAt(9) + "" + cpf.charAt(10);

    if (digits === cpfDigits) {
        return true;
    } else return false;
}

/**
 * Função para mostrar mensagem de erro no input.
 * */
const setError = (element, message) => {
    const formGroup = element.parentElement;
    const errorDisplay = formGroup.querySelector('.error');

    errorDisplay.innerText = message;
    formGroup.classList.add('error');
    formGroup.classList.remove('success');
}

/**
 * Função para mostrar mensagem de sucesso no input.
 * */
const setSuccess = element => {
    const formGroup = element.parentElement;
    const errorDisplay = formGroup.querySelector('.error');

    errorDisplay.innerText = '';
    formGroup.classList.add('success');
    formGroup.classList.remove('error');
};

/**
 * Função para mostrar mensagem de sucesso.
 * */
function setMessageSuccess(message, type) {
    alertAdd.style.display = 'block';
    alertAdd.className = "alert alert-success text-center";
    alertAdd.innerText = message;
    if (type === "add") {
        resetInputs();
    }
}

/**
 * Função para mostrar mensagem de erro.
 * */
function setMessageError(message) {
    alertAdd.style.display = 'block';
    alertAdd.className = "alert alert-danger text-center";
    alertAdd.innerText = message;
}

/**
 * Função para resetar inputs.
 * */
function resetInputs() {
    name.value = "";
    email.value = "";
    cpf.value = "";
    date.value = "";

    const formGroup = name.parentElement;
    formGroup.classList.remove('success');

    const formGroup2 = email.parentElement;
    formGroup2.classList.remove('success');

    const formGroup3 = cpf.parentElement;
    formGroup3.classList.remove('success');

    const formGroup4 = date.parentElement;
    formGroup4.classList.remove('success');
}

/**
 * Função para reload da página professor.html.
 * */
function reload() {
    location.reload();
}

/**
 * Função para informar se email e cpf já existem.
 * */
function duplicateValues(validate) {
    if (validate.emailExists) {
        setError(email, "Email já em uso!");
    }
    if (validate.cpfExists) {
        setError(cpf, "CPF já em uso!");
    }
}
const alertAdd = document.getElementById("alertAdd");
const alertDelete = document.getElementById("alertDelete");
const form = document.getElementById('form');
const email = document.getElementById('email');
const name = document.getElementById('name');
const date = document.getElementById('date');
const cpf = document.getElementById('cpf');
let id;
let cpfSemMascara;
let maxYear;
let minYear;
const degree = document.getElementById('degree');
let validEmail = false;
let validDate = false;
let validCpf = false;
let validName = false;

$(document).ready(function () {
    $('#cpf').inputmask('999.999.999-99');

    const thisYear = new Date().getFullYear();
    minYear = thisYear - 25;
    maxYear = thisYear - 75;

    date.setAttribute("max", minYear+"-12-31")
    date.setAttribute("min", maxYear+"-01-01")
});

document.querySelectorAll(".updateProfessor").forEach(
    function (button) {
        button.addEventListener("click", function () {
            id = button.closest("tr").querySelector(".id").value;
            alertAdd.style.display = 'none';
            getProfessor(id);
        });
    });

document.querySelectorAll(".deleteProfessor").forEach(
    function (button) {
        button.addEventListener("click", function () {
            id = button.closest("tr").querySelector(".id").value;
            alertDelete.style.display = 'none';
        });
    });

form.addEventListener('submit', e => {
    e.preventDefault();
    alertAdd.style.display = 'none';
    validate();
    if (id > 0) {
        if (validCpf && validName && validEmail && validDate) {
            updateProfessor(id);
        }
    } else if (validCpf && validName && validEmail && validDate) {
            submit();
    }
});

function submit() {
    let formData = {
        name: name.value,
        email: email.value,
        cpf: cpfSemMascara,
        degree: degree.value,
        date: date.value
    }
    $.ajax({
        url: `/admin/professor/add`,
        method: 'POST',
        data: JSON.stringify(formData),
        contentType: 'application/json',
        success: function (jqXHR) {
            professorSaved(name.value, email.value, cpf.value, date.value);
        },
        error: function (error) {
            const validate = JSON.parse(error.responseText);
            if(error.status === 409) {
                duplicateValues(validate);
            } else {
                alertAdd.style.display = 'block';
                alertAdd.className = "alert alert-danger text-center";
                alertAdd.innerText = "Não foi possível adicionar o Professor!!";
            }
        }
    });
}

function getProfessor(id) {
    $.ajax({
        url: `/admin/professor/update/${id}`,
        method: 'GET',
        success: function (data,jqXHR) {
            name.value = data.name;
            cpf.value = data.cpf;
            email.value = data.email;
            degree.value = data.degree;
            date.value = data.date;
        },
        error: function (jqXHR) {
            if (jqXHR.status === 500) {
                alertAdd.style.display = 'block';
                alertAdd.className = "alert alert-danger text-center";
                alertAdd.innerText = "Não foi possível buscar o Professor!!";
            }
        }
    });
}

function updateProfessor(id) {
    let formData = {
        name: name.value,
        email: email.value,
        cpf: cpfSemMascara,
        degree: degree.value,
        date: date.value
    }
    $.ajax({
        url: `/admin/professor/update/${id}`,
        method: 'PUT',
        data: JSON.stringify(formData),
        contentType: 'application/json',
        success: function (jqXHR) {
            professorUpdated(name.value, email.value, cpf.value, date.value);
        },
        error: function (error) {
            const validate = JSON.parse(error.responseText);
            if (error.status === 409) {
                duplicateValues(validate);
            } else {
                alertAdd.style.display = 'block';
                alertAdd.className = "alert alert-danger text-center";
                alertAdd.innerText = "Não foi possível atualizar o Professor!!";
            }
        }
    });
}

function deleteProfessor() {
    $("#deleteButton").prop("disabled", false);
    $.ajax({
        type: 'DELETE',
        url: `/admin/professor/delete/${id}`,
        success: function (data) {
            location.reload();
        }
    }).fail(function (jqXHR) {
            alertDelete.style.display = 'block';
            $("#deleteButton").prop("disabled", true);
    });
}

function validate() {
    cpfSemMascara = $('#cpf').inputmask("unmaskedvalue");
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
    if (/^(\d)\1{10}/.test(cpfSemMascara) || !validateDigits(cpfSemMascara) || cpfSemMascara.length !== 11) {
        setError(cpf, "CPF não é válido!");
        validCpf = false;
    } else {
        setSuccess(cpf);
        validCpf = true;
    }

    if(dateValue<=minYear || dateValue>=maxYear){
        setSuccess(date); validDate = true;
    }
}

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

const setError = (element, message) => {
    const formGroup = element.parentElement;
    const errorDisplay = formGroup.querySelector('.error');

    errorDisplay.innerText = message;
    formGroup.classList.add('error');
    formGroup.classList.remove('success');
}

const setSuccess = element => {
    const formGroup = element.parentElement;
    const errorDisplay = formGroup.querySelector('.error');

    errorDisplay.innerText = '';
    formGroup.classList.add('success');
    formGroup.classList.remove('error');
};

function professorSaved(name, email, cpf, date) {
    alertAdd.style.display = 'block';
    alertAdd.className = "alert alert-success text-center";
    alertAdd.innerText = "Professor adicionado!!";
    name.innerHTML = "";
    email.innerHTML = "";
    cpf.innerHTML = "";
    date.value = "";
}

function professorUpdated(name, email, cpf, date) {
    alertAdd.style.display = 'block';
    alertAdd.className = "alert alert-success text-center";
    alertAdd.innerText = "Professor atualizado!!";
    name.innerHTML = "";
    email.innerHTML = "";
    cpf.innerHTML = "";
    date.value = "";
}

function resetModal() {
    name.innerHTML = "";
    email.innerHTML = "";
    cpf.innerHTML = "";
    date.innerHTML = "";
    location.reload();
}

function duplicateValues(validate){
    if(validate.emailExists){
        setError(email, "Email já em uso!");
    }
    if (validate.cpfExists){
        setError(cpf, "CPF já em uso!");
    }
}
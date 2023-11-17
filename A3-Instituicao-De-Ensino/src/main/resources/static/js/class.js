const alertAdd = document.getElementById("alertAdd");
const alertDelete = document.getElementById("alertDelete");
const form = document.getElementById('form');
const className = document.getElementById('className');
const classRoom = document.getElementById('classRoom');
const classTime = document.getElementById('classTime');
const classDay = document.getElementById('classDay');
const professor = document.getElementById('professor');
let id;
let validClassName = false;

/**
 * Coloca um evento em cada botão de atualizar, para pegar o id da turma
 * */
document.querySelectorAll(".putClass").forEach(
    function (button) {
        button.addEventListener("click", function () {
            id = button.closest("tr").querySelector(".id").value;
            alertAdd.style.display = 'none';
            getClass(id);
        });
    });

/**
 * Coloca um evento em cada botão de deletar, para pegar o id da turma
 * */
document.querySelectorAll(".deleteClass").forEach(
    function (button) {
        button.addEventListener("click", function () {
            id = button.closest("tr").querySelector(".id").value;
            alertDelete.style.display = 'none';
        });
    });

/**
 * Coloca um evento no botão salvar, para pegar o id da turma.
 * */
form.addEventListener('submit', e => {
    e.preventDefault();
    alertAdd.style.display = 'none';
    validate();
    if (id > 0) {
        putClass(id);
    } else if (validClassName) {
        postClass();
    }
});

/**
 * Função para pegar turma.
 * */
function getClass(id) {
    axios.get(`/admin/class/update/${id}`)
        .then(function (response) {
            const data = response.data;
            className.value = data.className;
            classRoom.value = data.classRoom;
            classTime.value = data.time;
            classDay.value = data.classday;
            professor.value = data.professorId;
        })
        .catch(function (error) {
            if (error.response.status === 404) {
                setMessageError("Não foi possível buscar a Turma!!")
            }
        });
}

/**
 * Função para validar nome da turma.
 * */
function validate() {
    const classNameValue = className.value;
    const regexClassName = new RegExp("[A-Za-zÀ-ÖØ-öø-ÿ\\s\\-']{7,}");

    if (!regexClassName.test(classNameValue)) {
        setError(className, "Nome não é válido!");
        validClassName = false;
    } else {
        setSuccess(className);
        validClassName = true;
    }
}

/**
 * Função para atualizar turma.
 * */
function putClass(id) {
    let formData = {
        className: className.value,
        classRoom: classRoom.value,
        professor: professor.value,
        time: classTime.value,
        classday: classDay.value
    }
    axios({
        method: 'put',
        url: `/admin/class/update/${id}`,
        data: JSON.stringify(formData),
        headers: {
            'Content-Type': 'application/json',
        }
    })
        .then(function (response) {
            setMessageSuccess(className,"Turma atualizada!!","update");
        })
        .catch(function (error) {
            if (error.response.status === 409) {
                setMessageError("Já existe uma turma com o mesmo dia, horário e sala cadastrada.");
            }
            if (error.response.status === 404) {
                setMessageError("Não foi possível atualizar a Turma!!");
            }
        });
}

/**
 * Função para adicionar turma.
 * */
function postClass() {
    let formData = {
        className: className.value,
        classRoom: classRoom.value,
        time: classTime.value,
        classday: classDay.value,
        professor: professor.value
    }
    axios({
        url: `/admin/class/add`,
        method: 'post',
        data: JSON.stringify(formData),
        headers: {
            'Content-Type': 'application/json',
        }
    })
        .then(function (response) {
            setMessageSuccess(className,"Turma adicionada!!", "add");
        })
        .catch(function (error) {
            if (error.response.status === 409) {
                setMessageError("Já existe uma turma com o mesmo dia, horário e sala cadastrada.");
            }
            if (error.response.status === 404) {
                setMessageError("Não foi possível adicionar a Turma.");
            }
        });
}

/**
 * Função para deletar turma.
 * */
function deleteClass() {
    axios({
        method: 'delete',
        url: `/admin/class/delete/${id}`,
    })
        .then(function (response) {
            location.reload();
        })
        .catch(function (error) {
            if (error.response.status === 404) {
                alertDelete.style.display = 'block';
            } else {
                console.log(error);
            }
        });
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
function setMessageSuccess(className, message,type) {
    alertAdd.style.display = 'block';
    alertAdd.className = "alert alert-success text-center";
    alertAdd.innerText = message;
    if(type === "add"){
        const formGroup = className.parentElement;
        formGroup.classList.remove('success');
        className.value = "";
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
 * Função para reload da página class.html.
 * */
function reload() {
    location.reload();
}
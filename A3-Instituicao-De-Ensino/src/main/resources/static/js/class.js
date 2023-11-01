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

document.querySelectorAll(".updateClass").forEach(
    function (button) {
        button.addEventListener("click", function () {
            id = button.closest("tr").querySelector(".id").value;
            alertAdd.style.display = 'none';
            getClass(id);
        });
    });

document.querySelectorAll(".deleteClass").forEach(
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
        updateClass(id);
    } else if (validClassName) {
            submit();
    }
});

function validate() {

    const classNameValue = className.value;

    let regexClassName = new RegExp("[A-Za-zÀ-ÖØ-öø-ÿ\\s\\-']{7,}");

    if (!regexClassName.test(classNameValue)) {
        setError(className, "Nome não é válido!");
        validClassName = false;
    } else {
        setSuccess(className);
        validClassName = true;
    }
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

function submit() {
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
            classSaved(className);
        })
        .catch(function (error) {
            if (error.response.status === 500) {
                alertAdd.style.display = 'block';
                alertAdd.className = "alert alert-danger text-center";
                alertAdd.innerText = "Não foi possível adicionar a Turma!!";
            }
        });
}

function classSaved(className) {
    alertAdd.style.display = 'block';
    alertAdd.className = "alert alert-success text-center";
    alertAdd.innerText = "Turma adicionada!!";
    const formGroup = className.parentElement;
    formGroup.classList.remove('success');
    className.value = "";
}

function classUpdated() {
    alertAdd.style.display = 'block';
    alertAdd.className = "alert alert-success text-center";
    alertAdd.innerText = "Turma atualizada!!";
}

function deleteClass() {
    axios({
        method: 'delete',
        url: `/admin/class/delete/${id}`,
    })
        .then(function (response) {
            location.reload();
        })
        .catch(function (error) {
            alertDelete.style.display = 'block';
        });
}

function getClass(id) {
    console.log(professor.value + " "+ professor.innerText)
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
            if (error.response.status === 500) {
                alertAdd.style.display = 'block';
                alertAdd.className = "alert alert-danger text-center";
                alertAdd.innerText = "Não foi possível buscar a Turma!!";
            }
        });
}

function updateClass(id) {
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
            classUpdated();
        })
        .catch(function (error) {
            if (error.response.status === 500) {
                alertAdd.style.display = 'block';
                alertAdd.className = "alert alert-danger text-center";
                alertAdd.innerText = "Não foi possível atualizar a Turma!!";
            }
        });
}

function resetModal() {
    className.innerHTML = "";
    location.reload();
}
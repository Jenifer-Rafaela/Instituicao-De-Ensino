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
    $.ajax({
        url: `/admin/class/add`,
        method: 'POST',
        data: JSON.stringify(formData),
        contentType: 'application/json',
        success: function (jqXHR) {
            classSaved(className.value);
        },
        error: function (jqXHR) {
            if (jqXHR.status === 500) {
                alertAdd.style.display = 'block';
                alertAdd.className = "alert alert-danger text-center";
                alertAdd.innerText = "Não foi possível adicionar a Turma!!";
            }
        }
    });
}

function classSaved(className) {
    alertAdd.style.display = 'block';
    alertAdd.className = "alert alert-success text-center";
    alertAdd.innerText = "Turma adicionada!!";
    className.innerHTML = "";
}

function classUpdated(className) {
    alertAdd.style.display = 'block';
    alertAdd.className = "alert alert-success text-center";
    alertAdd.innerText = "Turma atualizada!!";
    className.innerHTML = "";

}

function deleteClass() {
    $.ajax({
        type: 'DELETE',
        url: `/admin/class/delete/${id}`,
        success: function (data) {
            location.reload();
        }
    }).fail(function (jqXHR) {
        if(jqXHR.status === 500){
            alertDelete.style.display = 'block';
        }
    });
}

function getClass(id) {
    $.ajax({
        url: `/admin/class/update/${id}`,
        method: 'GET',
        success: function (data) {
            className.value = data.className;
            classRoom.value = data.classRoom;
            classTime.value = data.time;
            classDay.value = data.classday;
        },
        error: function (jqXHR) {
            if (jqXHR.status === 500) {
                alertAdd.style.display = 'block';
                alertAdd.className = "alert alert-danger text-center";
                alertAdd.innerText = "Não foi possível buscar a Turma!!";
            }
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
    $.ajax({
        url: `/admin/class/update/${id}`,
        method: 'PUT',
        data: JSON.stringify(formData),
        contentType: 'application/json',
        success: function () {
            classUpdated(className.value);
        },
        error: function (jqXHR) {
            if(jqXHR.status === 500){
                alertAdd.style.display = 'block';
                alertAdd.className = "alert alert-danger text-center";
                alertAdd.innerText = "Não foi possível atualizar a Turma!!";
            }
        }
    });
}

function resetModal() {
    className.innerHTML = "";
    location.reload();
}
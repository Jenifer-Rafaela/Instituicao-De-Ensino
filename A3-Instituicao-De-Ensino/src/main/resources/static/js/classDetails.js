const classId = document.getElementById("idClass").value;
const alert = document.getElementById("alert");
const tbodyStudent = document.getElementById('tbodyStudent');
let keyword = document.getElementById("keyword");
let ra;

document.querySelectorAll(".removeStudent").forEach(
    function (button) {
        button.addEventListener("click", function () {
            ra = button.closest("tr").querySelector("td:first-child").textContent;
        });
    });

function searchButton() {
    alert.style.display = 'none'
    document.getElementById("table").style.display = "table";
    const time = document.getElementById("time").innerText;


    if (keyword.value != null && keyword.value.trim() != '') {
        axios.get("/admin/class/search/student", {
            params: {
                keyword: keyword.value,
                time: time
            }
        })
            .then(function (response) {
                let tbodyStudent = document.getElementById('tbodyStudent');
                while (tbodyStudent.firstChild) {
                    tbodyStudent.removeChild(tbodyStudent.firstChild);
                }

                for (let i = 0; i < response.data.length; i++) {
                    console.log(response.data[i])
                    createTable(response.data[i]);
                }
                keyword.value = "";
                console.log("keyword: "+keyword.value);
            })
            .catch(function (error) {
                if (error.response.status === 404) {
                    studentNotFound();
                }
            });
    }
}

function resetKeyword() {
    keyword.value = "";
    let tbodyStudent = document.getElementById('tbodyStudent');
    while (tbodyStudent.firstChild) {
        tbodyStudent.removeChild(tbodyStudent.firstChild);
    }
    location.reload();
}

function addStudentToClass(ra) {
    alert.style.display = 'none';
    axios({
        url: `/admin/class/add/student/${classId}/${ra}`,
        method: 'post'
    })
        .then(function (response) {
            alert.style.display = 'block';
        })
        .catch(function (error) {
            if (error.response.status === 409) {
                studentAlreadyExistsInClass();
            }
        });
}

function removeStudent() {
    axios({
        method: 'delete',
        url: `/admin/class/remove/student/${classId}/${ra}`,
    })
        .then(function (response) {
            location.reload();
        })
        .catch(function (error) {
            console.log("Erro ao buscar usuário: " + ra);
        });
}

function studentNotFound() {
    alert.style.display = 'block';
    alert.className = "alert alert-danger";
    alert.innerText = "Aluno não encontrado!";
}

function studentAlreadyExistsInClass() {
    alert.style.display = 'block';
    alert.className = "alert alert-info";
    alert.innerText = "Aluno já foi adicionado a turma!";
}

function createTable(data) {
    const newRow = document.createElement('tr');

    // Coluna ID
    const idCell = document.createElement('td');
    idCell.textContent = data.id;
    newRow.appendChild(idCell);

    // Coluna Nome
    const nameCell = document.createElement('td');
    nameCell.textContent = data.name;
    newRow.appendChild(nameCell);

    // Coluna Botão
    const buttonCell = document.createElement('td');
    const addButton = document.createElement('button');
    addButton.setAttribute('type', 'submit');
    addButton.setAttribute('id', 'liveAlertBtn');
    addButton.className = 'btn btn-info btn-sm';
    addButton.textContent = 'Adicionar';
    addButton.addEventListener('click', function () {
        addStudentToClass(data.id);
    });

    buttonCell.appendChild(addButton);
    newRow.appendChild(buttonCell);

    tbodyStudent.appendChild(newRow);
}

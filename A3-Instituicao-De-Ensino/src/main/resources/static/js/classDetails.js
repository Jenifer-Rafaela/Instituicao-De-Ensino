const classId = document.getElementById("idClass").value;
const alert = document.getElementById("alert");
const tbodyStudent = document.getElementById('tbodyStudent');
const time = document.getElementById("time").innerText;
const table = document.getElementById("table");
let ra;
let keyword = document.getElementById("keyword");

/**
 * Coloca um evento em cada botão de remover, pega o id do estudante
 * */
document.querySelectorAll(".removeStudent").forEach(
    function (button) {
        button.addEventListener("click", function () {
            ra = button.closest("tr").querySelector("td:first-child").textContent;
        });
    });

/**
 * Função para procurar estudantes por palavra chave.
 * */
function searchButton() {
    alert.style.display = 'none'

    if (keyword.value != null && keyword.value.trim() != '') {
        axios.get("/admin/class/search/student", {
            params: {
                keyword: keyword.value,
                time: time
            }
        })
            .then(function (response) {
                table.style.display = 'table';
                while (tbodyStudent.firstChild) {
                    tbodyStudent.removeChild(tbodyStudent.firstChild);
                }

                const post = response.data.map((item) => {
                    let itens = []
                        itens.push(`<tr><td>${item.id}</td><td>${item.name}</td><td><button type="submit" class="btn btn-info btn-sm" id="liveAlertBtn" onclick="addStudentToClass('${item.id}')">Adicionar</button></td></tr>`);
                    return itens;
                });
                tbodyStudent.innerHTML = post.join('');
                keyword.value = "";
            })
            .catch(function (error) {
                if (error.response.status === 404) {
                    studentNotFound();
                }
            });
    }
}

/**
 * Função para incluir estudante na turma.
 * */
function addStudentToClass(ra) {
    alert.style.display = 'none';
    axios({
        url: `/admin/class/add/student/${classId}/${ra}`,
        method: 'post'
    })
        .then(function (response) {
            studentMessage("Aluno adicionado com sucesso!","success")
        })
        .catch(function (error) {
            if (error.response.status === 409) {
                studentMessage("Aluno já foi adicionado a turma!","info")
            } else console.log(error)
        });
}

/**
 * Função para remover estudante da turma.
 * */
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

/**
 * Função para mostrar mensagem de aluno não encontrado.
 * */
function studentNotFound() {
    table.style.display = 'none';
    keyword.value = "";
    alert.style.display = 'block';
    alert.className = "alert alert-danger text-center";
    alert.innerText = "Aluno não encontrado!";
}

/**
 * Função para mostrar mensagem de sucesso ou de informação.
 * */
function studentMessage(message,alertType) {
    alert.style.display = 'block';
    alert.className = "alert text-center alert-"+alertType;
    alert.innerText = message;
}

/**
 * Função para da página classDetails.html.
 * */
function reload() {
    let tbodyStudent = document.getElementById('tbodyStudent');
    while (tbodyStudent.firstChild) {
        tbodyStudent.removeChild(tbodyStudent.firstChild);
    }
    location.reload();
}
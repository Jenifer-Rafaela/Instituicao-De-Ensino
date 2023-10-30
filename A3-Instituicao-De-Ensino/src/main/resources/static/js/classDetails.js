const classId = document.getElementById("idClass").value;
const alert = document.getElementById("alert");
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
    const keyword = document.getElementById("keyword").value;

    if (keyword != null && keyword.trim() != '') {
        $.ajax({
            type: 'GET',
            url: "/admin/class/search/student",
            data: {
                "keyword": keyword,
                "time": time
            },
            success: function (data) {
                $('#tbodyStudent > tr').remove();
                for (let i = 0; i < data.length; i++) {
                    $('#tbodyStudent').append('<tr>' +
                        '<td>' + data[i].id + '</td>' +
                        '<td>' + data[i].name + '</td>' +
                        '<td><button type="submit" class="btn btn-info btn-sm" id="liveAlertBtn" onclick="addStudentToClass(' + data[i].id + ')">Adicionar</button></td>' +
                        '</tr>')
                }
                $('#keyword').val("");
            }
        }).fail(function (jqXHR, errorThrown) {
            if (jqXHR.status === 404) {
                studentNotFound();
            }
        });
    }
}

function resetKeyword() {
    $('#keyword').val("");
    $('#tbodyStudent > tr').remove();
    location.reload();
}

function addStudentToClass(ra) {
    alert.style.display = 'none';
    $.ajax({
        type: 'POST',
        url: `/admin/class/add/student/${classId}/${ra}`,
        success: function (data) {
            alert.style.display = 'block';
        }
    }).fail(function (jqXHR) {
        if (jqXHR.status === 409) {
            studentAlreadyExistsInClass();
        }
    });
}

function removeStudent() {
    $.ajax({
        type: 'DELETE',
        url: `/admin/class/remove/student/${classId}/${ra}`,
        success: function (data) {
            location.reload();
        }
    }).fail(function (jqXHR) {
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



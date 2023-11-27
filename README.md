# Gerenciamento de Professores, Turmas e Estudantes de uma Instituição de Ensino
O projeto é uma plataforma de gestão educacional que oferece recursos eficientes para administrar e organizar informações relacionadas a professores, alunos e turmas em uma instituição de ensino. Esta aplicação oferece um ambiente centralizado para gerenciar o corpo docente, alunos matriculados e suas respectivas turmas, facilitando a administração.
<h4 align="center"> 
    :white_check_mark:  Projeto Finalizado  :white_check_mark:
</h4>

# Índice 
* [Tecnologias](#Tecnologias)
* [Funcionalidades do projeto](#Funcionalidades-do-Projeto)
* [Diagramas do Projeto](#Diagramas-Do-Projeto)
* [Testes Unitários](#Testes-Unitários)
  
# Tecnologias
- IDE IntelliJ;
- Spring Framework;
- padrão de arquitetura MVC;
- Maven;
- Thymeleaf;
- HTML;
- CSS;
- JavaScript;
- Banco de dados memória: H2;
- Testes unitários: JUnit.

# Funcionalidades Do Projeto
- `Autenticação e Autorização`: Controle de acesso por meio de autenticação de usuários (login/logout).
- `Validações`:
  - Nomes precisam ter pelo menos três letras;
  - Verifica se o email é valido e único;
  - A data de nascimento para estudantes é a partir de 14 até 75 anos;
  - A data de nascimento para professores é a partir de 25 até 70 anos;
  - Verifica se o CPF é valido e único;
  - Verifique se a combinação de sala, horário e dias da semana é única.
- `Login`: Apenas usuarios com a permissão de ADMIN tem acesso ao sistema.

![login](https://github.com/Jenifer-Rafaela/Instituicao-De-Ensino/assets/100365167/6dea9859-1af8-4449-bb46-0fb6e3a09ccc)

- `Login Quando não tem Permissão`:

![Login Sem Permissao](https://github.com/Jenifer-Rafaela/Instituicao-De-Ensino/assets/100365167/8bf4032b-0941-4b2e-97c5-e97fdcaf3039)
  
- `Home`:

![home](https://github.com/Jenifer-Rafaela/Instituicao-De-Ensino/assets/100365167/9b928fbe-18a6-413b-944f-264e6a04fa80)
  
- `Professores`: É possível ver os professores cadastrados.

![professores](https://github.com/Jenifer-Rafaela/Instituicao-De-Ensino/assets/100365167/3a295e42-4007-4012-81b9-b588ad39c940)
  
- `Cadastrar Professor`: É possível cadastrar professor, informando: nome, email, cpf, data de nascimento e titulação.

![Professor Cadastrar](https://github.com/Jenifer-Rafaela/Instituicao-De-Ensino/assets/100365167/e56bb431-de74-4543-b4fe-f4ceeab72ca9)

- `Cadastrar Professor com Email e CPF inválido`:

![Cadastro com Cpf e Email inválidos](https://github.com/Jenifer-Rafaela/Instituicao-De-Ensino/assets/100365167/88b99eef-2ac4-4ee3-b44c-04989eadf45d)
  
- `Atualizar Professor`: É possível atualizar professor.

![Professores Atualizar](https://github.com/Jenifer-Rafaela/Instituicao-De-Ensino/assets/100365167/9523281a-2977-4b9d-8e8a-4a9c0ab6697f)
  
- `Remover Professor Quando é Responsável por uma turma`: Não é possível remover professor.

![Professor Remover - não aceito](https://github.com/Jenifer-Rafaela/Instituicao-De-Ensino/assets/100365167/c0e9f0d5-3369-4294-bf3c-b86d47aadbd2)
  
- `Remover Professor Quando não é Responsável por uma turma`: É possível remover professor.

![Professor Remover](https://github.com/Jenifer-Rafaela/Instituicao-De-Ensino/assets/100365167/229ee4e0-c1e1-4d49-9644-da94814778b2)
  
- `Detalhes Professor`: É possível ver todas as informações do professor como quais turmas ele é responsável.

![Professor Detalhes](https://github.com/Jenifer-Rafaela/Instituicao-De-Ensino/assets/100365167/70fb73a2-d997-47b7-a2d7-ed130e9cde7d)
  
- `Estudantes`: É possível ver os estudantes cadastrados.

![estudantes](https://github.com/Jenifer-Rafaela/Instituicao-De-Ensino/assets/100365167/6fd30ecb-3ef0-428c-959a-495fd78ee40e)
  
- `Cadastrar Estudante`: É possível cadastrar estudante, informando: nome, email, CPF, data de nascimento e turno.

![Estudante Cadastrar](https://github.com/Jenifer-Rafaela/Instituicao-De-Ensino/assets/100365167/06ed208f-b85a-44ce-bd2b-d9794069d48d)

- `Atualizar Estudante`: É possível atualizar estudante.

![Atualizar Estudante](https://github.com/Jenifer-Rafaela/Instituicao-De-Ensino/assets/100365167/ec85bf4f-e76a-4c7e-ba9d-21f3eb7b7718)

- `Remover Estudante`: É possível remover estudante.

![Remover Estudante](https://github.com/Jenifer-Rafaela/Instituicao-De-Ensino/assets/100365167/d83b652e-e7cc-441d-9fcc-55e898c68647)

- `Detalhes Estudante`: É possível ver todas as informações do estudante como quais turmas ele está matriculado.

![Detalhes Estudante](https://github.com/Jenifer-Rafaela/Instituicao-De-Ensino/assets/100365167/716ccc7d-d9cf-40f1-903f-fb1743eca4dd)

- `Turmas`: É possível ver as turmas cadastradas.

![Turmas](https://github.com/Jenifer-Rafaela/Instituicao-De-Ensino/assets/100365167/597f6c35-5730-4a4c-bc90-12de919f0986)

- `Cadastrar Turma`: É possível cadastrar turma, informando: UC, Sala, Horário, Dias da Semana e Professor Responsável.

![Cadastrar Turmas](https://github.com/Jenifer-Rafaela/Instituicao-De-Ensino/assets/100365167/6ead0801-4d2b-4789-8dc2-26ba320bbe4a)

- `Cadastrar Turma Quando sala, horário e dias da semana não estão disponíveis`: não é possível cadastrar a turma.

![Cadastrar Turma inválida](https://github.com/Jenifer-Rafaela/Instituicao-De-Ensino/assets/100365167/835fa1e6-e8b7-4c79-81d8-d9731dbb2ac5)

- `Atualizar Turma`: É possível atualizar turma.

![Atualizar Turmas](https://github.com/Jenifer-Rafaela/Instituicao-De-Ensino/assets/100365167/eb02fe04-0fc9-4c47-85f0-ecbe7b438265)

- `Remover Turma`: É possível remover turma.

![Remover Turmas](https://github.com/Jenifer-Rafaela/Instituicao-De-Ensino/assets/100365167/1a4f2dfb-e3e2-47d4-8d3f-3157f0bf8b18)

- `Detalhes Turma`: É possível ver todas as informações da turma e quais alunos estão matriculados nela.

![Detalhes Turma ](https://github.com/Jenifer-Rafaela/Instituicao-De-Ensino/assets/100365167/b204f129-68b8-4c49-8ac4-10d9b1097687)

- `Adicionar Aluno`: É possível adicionar aluno informando a RA ou número que contenha na RA. Apenas alunos com o mesmo turno da turma serão mostrados.

![Adicionar Aluno](https://github.com/Jenifer-Rafaela/Instituicao-De-Ensino/assets/100365167/2a2a66c0-cc26-46ba-978a-c85f74caff04)

- `Remover Aluno`: É possível remover aluno da turma.

![Remover Aluno](https://github.com/Jenifer-Rafaela/Instituicao-De-Ensino/assets/100365167/3a808868-582f-4254-879d-4219b417eaec)

# Diagramas Do Projeto
- Diagrama MER - modelo James Martin
![MER PROJETO INSTITUIÇÃO DE ENSINO (1)](https://github.com/Jenifer-Rafaela/Instituicao-De-Ensino/assets/100365167/42cec73e-2321-4030-b1ba-4b3d805881c9)

- Esquema do Banco de Dados
![CURSO](https://github.com/Jenifer-Rafaela/Instituicao-De-Ensino/assets/100365167/db08b747-acd2-4a56-982c-80bc18b95938)

# Testes Unitários

- Testando classe que se comunica com banco de dados: ClassesRepository

![ClassesRepository](https://github.com/Jenifer-Rafaela/Instituicao-De-Ensino/assets/100365167/7115ed5a-3ed4-4d07-bd48-b5d09aba950a)

- Testando classe que se comunica com banco de dados: ProfessorRepository

![ProfessorRepository](https://github.com/Jenifer-Rafaela/Instituicao-De-Ensino/assets/100365167/e02cb008-c883-4898-a179-91062f2ea3f7)

- Testando classe que se comunica com banco de dados: StudentRepository

![StudentRepository](https://github.com/Jenifer-Rafaela/Instituicao-De-Ensino/assets/100365167/fb2a6d33-4172-4975-b522-a3a2f823506b)

- Testando classe: ClassesService

![ClassesService](https://github.com/Jenifer-Rafaela/Instituicao-De-Ensino/assets/100365167/2743214b-b7e9-49a9-8f56-353629f74f9f)

- Testando classe: ProfessorService

![ProfessorService](https://github.com/Jenifer-Rafaela/Instituicao-De-Ensino/assets/100365167/ee966da6-cab5-4963-94e2-36e48e398953)

- Testando classe: StudentService

![StudentService](https://github.com/Jenifer-Rafaela/Instituicao-De-Ensino/assets/100365167/9eb52526-4ad8-470d-ad7c-b469dff6be14)


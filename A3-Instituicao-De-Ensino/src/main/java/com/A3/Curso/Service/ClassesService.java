package com.A3.Curso.Service;

import com.A3.Curso.DTO.Classes.ClassesDTO;
import com.A3.Curso.DTO.Classes.ClassesProfessorResponseDTO;
import com.A3.Curso.DTO.Classes.ClassesResponseDTO;
import com.A3.Curso.DTO.Classes.SearchDTO;
import com.A3.Curso.DTO.Professors.ProfessorClassResponseDTO;
import com.A3.Curso.DTO.Students.StudentResponseDTO;
import com.A3.Curso.Enum.ClassDay;
import com.A3.Curso.Enum.ClassRoom;
import com.A3.Curso.Enum.Time;
import com.A3.Curso.Model.Classes;
import com.A3.Curso.Model.Professor;
import com.A3.Curso.Model.Student;
import com.A3.Curso.Repository.ClassesRepository;
import com.A3.Curso.Repository.ProfessorRepository;
import com.A3.Curso.Repository.StudentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClassesService {
    private final ClassesRepository classRepository;
    private final StudentRepository studentRepository;
    private final ProfessorRepository professorRepository;

    public ClassesService(ClassesRepository classRepository, StudentRepository studentRepository, ProfessorRepository professorRepository) {
        this.classRepository = classRepository;
        this.studentRepository = studentRepository;
        this.professorRepository = professorRepository;
    }

    private ProfessorClassResponseDTO professorClassResponseDTO = new ProfessorClassResponseDTO();
    private ClassesProfessorResponseDTO classesProfessorResponseDTO = new ClassesProfessorResponseDTO();
    private ClassesResponseDTO classesResponseDTO = new ClassesResponseDTO();
    private StudentResponseDTO studentResponseDTO = new StudentResponseDTO();

    /**
     * <strong>Método para disponibilizar informações das turmas na view (class.html)</strong>
     * @param model onde será atribuída as informações das turmas.
     * @return model - para ser utilizado na view (class.html).
     * */
    public Model getClasses(Model model) {
        model.addAttribute("ClassTime", Time.values());
        model.addAttribute("ClassDay", ClassDay.values());
        model.addAttribute("ClassRoom", ClassRoom.values());
        model.addAttribute("Professors", professorClassResponseDTO.professorToDTOList(professorRepository.findAll()));
        model.addAttribute("Classes", classesResponseDTO.classesToDTOList(classRepository.findAll()));
        return model;
    }

    /**
     * <strong>Método para incluir uma turma no banco de dados</strong>
     * @param classesDTO informações recebidas da view (class.html).
     * @return ResponseEntity - com status 200, se inclusão foi feita.
     * @throws ResponseEntity com status 500.
     * */
    public ResponseEntity<String> addClass(ClassesDTO classesDTO) {
        Optional<Professor> professor = professorRepository.findById(classesDTO.getProfessor());
        if (professor.isPresent()) {
            classRepository.save(classesDTO.DTOToClass(professor.get()));
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(500).build();
    }

    /**
     * <strong>Método para pegar as informações da turma</strong>
     * @param id id da turma a ser recuperada do banco de dados.
     * @return ResponseEntity - com informações da turma para a view (class.html) e status 200.
     * @throws ResponseEntity com status 500.
     * */
    public ResponseEntity<ClassesProfessorResponseDTO> getClass(long id) {
        Optional<Classes> classes = classRepository.findById(id);
        if (classes.isPresent()) {
            return ResponseEntity.status(200).body(classesProfessorResponseDTO.classesToDTO(classes.get()));
        }
        return ResponseEntity.status(500).build();
    }

    /**
     * <strong>Método para atualizar uma turma no banco de dados</strong>
     * @param id id da turma a ser atualizada no banco de dados.
     * @param classesDTO informações recebidas da view(class.html) para atualização.
     * @return ResponseEntity - com status 200, se atualização foi feita.
     * @throws ResponseEntity com status 500.
     * */
    public ResponseEntity<String> putClass(long id, ClassesDTO classesDTO) {
        Optional<Classes> classes = classRepository.findById(id);
        Professor professor = professorRepository.findById(classesDTO.getProfessor()).get();
        if (classes.isPresent()) {
            classRepository.save(classesDTO.DTOToClassUpdate(classes.get(), professor));
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(500).build();
    }

    /**
     * <strong>Método para a excluir uma turma do banco de dados</strong>
     * @param classId id da turma que será excluída.
     * @return ResponseEntity - com status 200, se exclusão foi feita.
     * @throws ResponseEntity com status 500.
     * */
    public ResponseEntity<String> deleteClass(long classId) {
        Optional<Classes> classes = classRepository.findById(classId);

        if (classes.isPresent()) {
            List<Student> students = classes.get().getStudents();
            if (!students.isEmpty()) classRepository.removeAllStudentsFromClass(classId);
            classRepository.deleteById(classId);
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(500).build();
    }

    /**
     * <strong>Método para ver detalhes de uma turma</strong>
     * @param id id da turma a ser recuperada do banco de dados.
     * @param model onde será atribuída as informações da turma.
     * @return ModelAndView - redireciona para a página classDetails.html.
     * @throws ModelAndView redireciona para a página error.html
     * */
    public ModelAndView classDetails(long id, Model model) {
        Optional<Classes> classes = classRepository.findById(id);
        if (classes.isPresent()) {
            model.addAttribute("class", classesProfessorResponseDTO.classesToDTO(classes.get()));
            model.addAttribute("Students", studentResponseDTO.studentToDTOList(studentRepository.findAllByClassesId(id)));
            return new ModelAndView("classDetails");
        } return new ModelAndView("error");
    }

    /**
     * <strong>Método para incluir um estudante a uma turma</strong>
     * @param classId id da turma na qual o estudante será adicionado.
     * @param studentId id do estudante a ser adicionado na turma.
     * @return ResponseEntity - com status 200, se inclusão foi feita.<br>
     * ResponseEntity com status 409, se estudante já existir na turma.
     * @throws ResponseEntity com status 500.
     * */
    public ResponseEntity<String> addStudentsToClass(long classId, long studentId) {
        Classes classes = classRepository.findById(classId).get();
        if (classRepository.studentExistInClass(studentId, classId) != null) {
            return ResponseEntity.status(409).build();
        }
        Optional<Student> student = studentRepository.findById(studentId);

        if (student.isPresent()) {
            student.get().getClasses().add(classes);
            studentRepository.save(student.get());
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(500).build();
    }

    /**
     * <strong>Método para buscar estudantes com o mesmo turno da turma no banco de dados</strong>
     * @param keyword ra ou número do estudante que será recuperado do banco de dados.
     * @param time turno da turma.
     * @return ResponseEntity - lista de estudantes com status 200.
     * @throws ResponseEntity com status 404, caso estudante não exista.
     * */
    public ResponseEntity<List<SearchDTO>> searchStudent(long keyword, String time) {
        String shift = Time.getTime(time);
        List<Student> students = studentRepository.findByKeyword(keyword, shift);
        List<SearchDTO> searchDTO = students
                .stream()
                .map(Student -> new SearchDTO(Student.getId(), Student.getName()))
                .collect(Collectors.toList());

        if (students.isEmpty()) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(searchDTO);
    }

    /**
     * <strong>Método para a remoção de um estudante em uma turma</strong>
     * @param classId id da turma da qual o estudante será removido.
     * @param studentId id do estudante que será removido da turma.
     * @return ResponseEntity - com status 200, se remoção foi feita.
     * @throws ResponseEntity com status 404.
     * */
    public ResponseEntity<String> removeStudentOfClass(long classId, long studentId) {
        try {
            classRepository.removeStudentFromClass(studentId, classId);
        } catch (Exception e) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).build();
    }
}

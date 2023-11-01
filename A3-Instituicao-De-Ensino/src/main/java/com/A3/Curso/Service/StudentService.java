package com.A3.Curso.Service;

import com.A3.Curso.DTO.Classes.ClassesProfessorResponseDTO;
import com.A3.Curso.DTO.Classes.ClassesResponseDTO;
import com.A3.Curso.DTO.Students.StudentDTO;
import com.A3.Curso.DTO.Students.StudentResponseDTO;
import com.A3.Curso.Enum.Shift;
import com.A3.Curso.Model.Classes;
import com.A3.Curso.Model.Student;
import com.A3.Curso.Repository.ClassesRepository;
import com.A3.Curso.Repository.ProfessorRepository;
import com.A3.Curso.Repository.StudentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final ClassesRepository classRepository;

    private StudentResponseDTO studentResponseDTO = new StudentResponseDTO();
    private ClassesResponseDTO classesResponseDTO = new ClassesResponseDTO();

    public StudentService(ClassesRepository classRepository, StudentRepository studentRepository) {
        this.classRepository = classRepository;
        this.studentRepository = studentRepository;
    }

    /**
     * <strong>Método para disponibilizar informações dos estudantes na view (students.html)</strong>
     * @param model onde será atribuído as informações dos estudantes.
     * @return model - para ser utilizado na view (estudantes.html).
     * */
    public Model getStudents(Model model) {
        model.addAttribute("Students", studentResponseDTO.studentToDTOList(studentRepository.findAll()));
        model.addAttribute("Shifts", Shift.values());
        return model;
    }

    /**
     * <strong>Método para incluir um estudante no banco de dados</strong>
     * @param studentDTO informações recebidas da view (students.html).
     * @return ResponseEntity - com status 200, se inclusão foi feita.
     * @throws ResponseEntity com status 409, se email e/ou cpf já estiver em uso.
     * */
    public ResponseEntity<Map<String, Boolean>> addStudent(StudentDTO studentDTO) {
        Optional<String> email = studentRepository.emailExists(studentDTO.getEmail(),0);
        Optional<String> cpf = studentRepository.cpfExists(studentDTO.getCpf(),0);

        if (!cpf.isPresent() && !email.isPresent()) {
            studentRepository.save(studentDTO.DTOToStudent());
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(409).body(validateEmailAndCpf(email, cpf));
    }

    /**
     * <strong>Método para ver detalhes de um estudante.</strong>
     * @param id id do estudante a ser recuperado do banco de dados.
     * @param model onde será atribuído as informações do estudante.
     * @return ModelAndView - redireciona para a página studentDetails.html.
     * @throws ModelAndView redireciona para a página error.html.
     * */
    public ModelAndView studentDetails(long id, Model model) {
        List<Classes> classes = classRepository.findClassesByStudentsId(id);
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            model.addAttribute("Student", studentResponseDTO.studentToDTO(student.get()));
            model.addAttribute("Classes", classesResponseDTO.classesToDTOList(classes));
            return new ModelAndView("studentDetails");
        }
        return new ModelAndView("professorDetails");
    }

    /**
     * <strong>Método para pegar as informações do estudante</strong>
     * @param id id do estudante a ser recuperado do banco de dados.
     * @return ResponseEntity - com informações do estudante para a view (students.html) e status 200.
     * @throws ResponseEntity com status 500.
     * */
    public ResponseEntity<StudentResponseDTO> getStudent(long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            return ResponseEntity.status(200).body(studentResponseDTO.studentToDTO(student.get()));
        }
        return ResponseEntity.status(500).build();
    }

    /**
     * <strong>Método para atualizar um estudante no banco de dados</strong>
     * @param id id do estudante a ser atualizado no banco de dados.
     * @param studentDTO informações recebidas da view(students.html) para atualização.
     * @return ResponseEntity - com status 200, se atualização foi feita.<br>
     * ResponseEntity - com status 409, se email e/ou cpf já estiver em uso.
     * @throws ResponseEntity com status 500.
     * */
    public ResponseEntity<Map<String, Boolean>> putStudent(long id, StudentDTO studentDTO) {
        Optional<String> email = studentRepository.emailExists(studentDTO.getEmail(), id);
        Optional<String> cpf = studentRepository.cpfExists(studentDTO.getCpf(), id);
        Optional<Student> student = studentRepository.findById(id);

        if (student.isPresent()) {
            if (!email.isPresent() && !cpf.isPresent()) {
                studentRepository.save(studentDTO.DTOToStudentUpdate(student.get()));
                return ResponseEntity.status(200).build();
            } return ResponseEntity.status(409).body(validateEmailAndCpf(email, cpf));
        }
        return ResponseEntity.status(500).build();
    }

    /**
     * <strong>Método para a excluir um estudante do banco de dados</strong>
     * @param id id do estudante que será excluído.
     * @return ResponseEntity - com status 200, se exclusão foi feita.
     * @throws ResponseEntity com status 409.
     * */
    public ResponseEntity<String> deleteStudent(long id) {
        try {
            studentRepository.deleteById(id);
            return ResponseEntity.status(200).build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }


    /**
     * <strong>Método para validar email e cpf</strong>
     * @param email email a ser validado.
     * @param cpf cpf a ser validado.
     * @return Map com chaves emailExists e cpfExists, e valores podendo ser true, se email ou cpf estiver em uso, e false, se email ou cpf não estiver em uso.
     * */
    public static Map<String, Boolean> validateEmailAndCpf(Optional<String> email, Optional<String> cpf) {
        Map<String, Boolean> response = new HashMap<>();
        if (email.isPresent()) {
            response.put("emailExists", true);
        } else response.put("emailExists", false);

        if (cpf.isPresent()) {
            response.put("cpfExists", true);
        } else response.put("cpfExists", false);
        return response;
    }
}

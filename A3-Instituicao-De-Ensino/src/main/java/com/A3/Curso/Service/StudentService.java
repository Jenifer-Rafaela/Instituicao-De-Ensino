package com.A3.Curso.Service;

import com.A3.Curso.DTO.Classes.ClassesProfessorResponseDTO;
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
    private ClassesProfessorResponseDTO classesResponseDTO = new ClassesProfessorResponseDTO();
    private final ProfessorRepository professorRepository;

    public StudentService(ClassesRepository classRepository, StudentRepository studentRepository,
                          ProfessorRepository professorRepository) {
        this.classRepository = classRepository;
        this.studentRepository = studentRepository;
        this.professorRepository = professorRepository;
    }

    public Model getStudents(Model model) {
        model.addAttribute("Students", studentResponseDTO.studentsToDTOList(studentRepository.findAll()));
        model.addAttribute("Shifts", Shift.values());
        return model;
    }

    public ResponseEntity<Map<String, Boolean>> addStudent(StudentDTO studentDTO) {
        Optional<String> email = studentRepository.emailExists(studentDTO.getEmail(),0);
        Optional<String> cpf = studentRepository.cpfExists(studentDTO.getCpf(),0);

        if (!cpf.isPresent() && !email.isPresent()) {
            studentRepository.save(studentDTO.DTOToStudent());
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(409).body(validateEmailAndCpf(email, cpf));
    }

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

    public ResponseEntity<String> deleteStudent(long id) {
        try {
            studentRepository.deleteById(id);
            return ResponseEntity.status(200).build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    public ResponseEntity<StudentResponseDTO> getStudent(long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            return ResponseEntity.status(200).body(studentResponseDTO.studentsToDTO(student.get()));
        }
        return ResponseEntity.status(500).build();
    }

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

    public ModelAndView studentDetails(long id, Model model) {
        List<Classes> classes = classRepository.findClassesByStudentsId(id);
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            model.addAttribute("Student", studentResponseDTO.studentsToDTO(student.get()));
            model.addAttribute("Classes", classesResponseDTO.classesToDTOList(classes));

        }
        return new ModelAndView("studentDetails");
    }

}

package com.A3.Trabalho.Service;

import com.A3.Trabalho.DTO.Students.StudentDTO;
import com.A3.Trabalho.DTO.Students.StudentResponseDTO;
import com.A3.Trabalho.Model.Classes;
import com.A3.Trabalho.Model.Student;
import com.A3.Trabalho.Repository.ClassesRepository;
import com.A3.Trabalho.Repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private ClassesRepository classesRepository;

    @InjectMocks
    private StudentService studentService;

    private StudentDTO studentDTO;
    private long studentId = 2L;

    @BeforeEach
    void setUp() {
        studentDTO = new StudentDTO("Jenifer", "jenifer2@gmail.com", "84892340792", "Noite", Date.valueOf("2000-05-13"));
    }

    @DisplayName("Testa método addStudent quando cpf e email estão disponíveis")
    @Test
    void When_addStudent_Expect_200() {
        Mockito.when(studentRepository.emailExists(studentDTO.DTOToStudent().getEmail())).thenReturn(false);
        Mockito.when(studentRepository.cpfExists(studentDTO.DTOToStudent().getCpf())).thenReturn(false);

        ResponseEntity<Map<String, Boolean>> responseEntity = studentService.addStudent(studentDTO);

        assertEquals(ResponseEntity.status(200).build(), responseEntity);
    }

    @DisplayName("Testa método addStudent quando email e cpf estão indisponíveis")
    @Test
    void When_addStudent_Expect_409() {
        Mockito.when(studentRepository.emailExists(studentDTO.DTOToStudent().getEmail())).thenReturn(true);
        Mockito.when(studentRepository.cpfExists(studentDTO.DTOToStudent().getCpf())).thenReturn(true);

        ResponseEntity<Map<String, Boolean>> responseEntity = studentService.addStudent(studentDTO);
        assertEquals(409, responseEntity.getStatusCode().value());
    }

    @DisplayName("Testa método studentDetails quando o estudante existe")
    @Test
    void When_studentDetails_Expect_viewStudentDetails() {
        List<Classes> classes = Arrays.asList(new Classes(), new Classes());
        Model model = Mockito.mock(Model.class);

        Mockito.when(classesRepository.findClassesByStudentsId(studentId)).thenReturn(classes);
        Mockito.when(studentRepository.findById(studentId)).thenReturn(Optional.of(studentDTO.DTOToStudent()));

        ModelAndView modelAndView = studentService.studentDetails(studentId, model);
        assertEquals("studentDetails", modelAndView.getViewName());
    }

    @DisplayName("Testa método studentDetails quando o estudante não existe")
    @Test
    void When_studentDetails_Expect_viewError() {
        List<Classes> classes = new ArrayList<>();
        Model model = Mockito.mock(Model.class);

        Mockito.when(classesRepository.findClassesByStudentsId(studentId)).thenReturn(classes);
        Mockito.when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        ModelAndView modelAndView = studentService.studentDetails(studentId, model);
        assertEquals("error", modelAndView.getViewName());
    }

    @DisplayName("Testa método getStudent quando o estudante existe")
    @Test
    void When_getStudent_Expect_200() {
        Student student = new Student(studentId, "Jenifer", "jenifer2@gmail.com", "84892340792", "Noite", Date.valueOf("2000-05-13"));

        Mockito.when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));

        ResponseEntity<StudentDTO> responseEntity = studentService.getStudent(student.getId());
        assertEquals(200, responseEntity.getStatusCode().value());
    }

    @DisplayName("Testa método getStudent quando o estudante não existe")
    @Test
    void When_getStudent_Expect_404() {
        Mockito.when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        ResponseEntity<StudentDTO> responseEntity = studentService.getStudent(studentId);

        assertEquals(404, responseEntity.getStatusCode().value());
    }

    @DisplayName("Testa método putStudent quando cpf e email estão disponíveis, e estudante existe")
    @Test
    void When_putStudent_Expect_200() {
        Mockito.when(studentRepository.findById(studentId)).thenReturn(Optional.of(new Student()));

        Mockito.when(studentRepository.emailExists(studentDTO.getEmail())).thenReturn(false);
        Mockito.when(studentRepository.cpfExists(studentDTO.getCpf())).thenReturn(false);

        ResponseEntity<Map<String, Boolean>> responseEntity = studentService.putStudent(studentId, studentDTO);
        assertEquals(200, responseEntity.getStatusCode().value());
    }

    @DisplayName("Testa método putStudent quando email e cpf estão indisponíveis, e estudante existe")
    @Test
    void When_putStudent_Expect_409() {
        Mockito.when(studentRepository.findById(studentId)).thenReturn(Optional.of(new Student()));

        Mockito.when(studentRepository.emailExists(studentDTO.getEmail())).thenReturn(true);
        Mockito.when(studentRepository.cpfExists(studentDTO.getCpf())).thenReturn(true);

        ResponseEntity<Map<String, Boolean>> responseEntity = studentService.putStudent(studentId, studentDTO);
        assertEquals(409, responseEntity.getStatusCode().value());
    }

    @DisplayName("Testa método deleteStudent quando o estudante existe")
    @Test
    void When_deleteStudent_Expect_200() {
        Mockito.when(studentRepository.findById(studentId)).thenReturn(Optional.of(new Student()));

        ResponseEntity responseEntity = studentService.deleteStudent(studentId);
        assertEquals(200, responseEntity.getStatusCode().value());
    }

    @DisplayName("Testa método deleteStudent quando o estudante não existe")
    @Test
    void When_deleteStudent_Expect_404() {
        Mockito.when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        ResponseEntity responseEntity = studentService.deleteStudent(studentId);
        assertEquals(404, responseEntity.getStatusCode().value());
    }

    @DisplayName("Testa método validateEmailAndCpf quando o email está disponível, e o cpf está indisponível")
    @Test
    void When_validateEmailAndCpf_Expect_emailFalse() {
        Map<String, Boolean> response = studentService.validateEmailAndCpf(false, true);

        assertEquals(false, response.get("emailExists").booleanValue());
        assertEquals(true, response.get("cpfExists").booleanValue());
    }

    @DisplayName("Testa método validateEmailAndCpf quando o email está indisponível, e o cpf está disponível")
    @Test
    void When_validateEmailAndCpf_Expect_cpfFalse() {
        Map<String, Boolean> response = studentService.validateEmailAndCpf(true,false);

        assertEquals(true, response.get("emailExists").booleanValue());
        assertEquals(false, response.get("cpfExists").booleanValue());
    }
}

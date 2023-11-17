package com.A3.Trabalho.Service;

import com.A3.Trabalho.DTO.Classes.ClassesDTO;
import com.A3.Trabalho.DTO.Classes.ClassesProfessorResponseDTO;
import com.A3.Trabalho.DTO.Classes.SearchDTO;
import com.A3.Trabalho.Model.Classes;
import com.A3.Trabalho.Model.Professor;
import com.A3.Trabalho.Model.Student;
import com.A3.Trabalho.Repository.ClassesRepository;
import com.A3.Trabalho.Repository.ProfessorRepository;
import com.A3.Trabalho.Repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClassesServiceTest {

    @Mock
    ClassesRepository classesRepository;

    @Mock
    StudentRepository studentRepository;

    @Mock
    ProfessorRepository professorRepository;

    @InjectMocks
    ClassesService classesService;

    private ClassesDTO classesDTO;
    private Professor professor;
    private Classes classes;
    private Student student;

    @BeforeEach
    void setUp() {
        professor = new Professor("Ricardo", "ricardo@gmail.com", "1234567891", "Mestre", Date.valueOf("1980-05-13"));
        classesDTO = new ClassesDTO("UC", "Sala 5", "08:40 - 11:40", "Segundas e Sextas", String.valueOf(professor.getId()));
        classes = new Classes("UC", "Sala 5", "08:40 - 11:40", "Segunda e Sexta", professor);
        student = new Student("Jenifer", "jenifer2@gmail.com", "84892340792", "Noite", Date.valueOf("2000-05-13"));
    }

    @DisplayName("Testa método addClass quando horário, dia e sala estão disponíveis, e o professor existe")
    @Test
    void When_addClass_Expect_200() {
        when(professorRepository.findById(professor.getId())).thenReturn(Optional.of(professor));
        when(classesRepository.classTimeAlreadyInUse(
                classesDTO.getClassday(), classesDTO.getTime(), classesDTO.getClassRoom())).thenReturn(false);

        ResponseEntity responseEntity = classesService.addClass(classesDTO);
        assertEquals(200, responseEntity.getStatusCode().value());
    }

    @DisplayName("Testa método addClass quando horário, dia e sala estão indisponíveis, e o professor existe")
    @Test
    void When_addClass_Expect_409() {
        when(professorRepository.findById(professor.getId())).thenReturn(Optional.of(professor));
        when(classesRepository.classTimeAlreadyInUse(
                classesDTO.getClassday(), classes.getTime(), classes.getClassRoom())).thenReturn(true);

        ResponseEntity responseEntity = classesService.addClass(classesDTO);
        assertEquals(409, responseEntity.getStatusCode().value());
    }

    @DisplayName("Testa método addClass quando horário, dia e sala estão disponíveis, e professor não existe")
    @Test
    void When_addClass_Expect_404() {
        when(professorRepository.findById(professor.getId())).thenReturn(Optional.empty());
        when(classesRepository.classTimeAlreadyInUse(
                classesDTO.getClassday(), classes.getTime(), classes.getClassRoom())).thenReturn(false);

        ResponseEntity responseEntity = classesService.addClass(classesDTO);
        assertEquals(404, responseEntity.getStatusCode().value());
    }

    @DisplayName("Testa método getClass quando a classe existe")
    @Test
    void When_getClass_Expect_200() {
        when(classesRepository.findById(classes.getId())).thenReturn(Optional.of(classes));
        ResponseEntity<ClassesProfessorResponseDTO> responseEntity = classesService.getClass(classes.getId());
        assertEquals(200, responseEntity.getStatusCode().value());
    }

    @DisplayName("Testa método getClass quando a classe não existe")
    @Test
    void When_getClass_Expect_404() {
        when(classesRepository.findById(classes.getId())).thenReturn(Optional.empty());
        ResponseEntity<ClassesProfessorResponseDTO> responseEntity = classesService.getClass(classes.getId());
        assertEquals(404, responseEntity.getStatusCode().value());
    }

    @DisplayName("Testa o método putClass quando o horário, dia e sala estão disponíveis, e tanto o professor quanto a classe existem")
    @Test
    void When_putClass_Expect_200() {
        when(classesRepository.findById(classes.getId())).thenReturn(Optional.of(classes));
        when(professorRepository.findById(professor.getId())).thenReturn(Optional.of(professor));
        when(classesRepository.classTimeAlreadyInUse(
                classesDTO.getClassday(), classesDTO.getTime(), classesDTO.getClassRoom())).thenReturn(false);

        ResponseEntity<ClassesProfessorResponseDTO> responseEntity =
                classesService.putClass(classes.getId(), classesDTO);

        assertEquals(200, responseEntity.getStatusCode().value());
    }

    @DisplayName("Testa método putClass quando horário, dia e sala estão disponíveis, e tanto o professor quanto a classe existem")
    @Test
    void When_putClass_Expect_409() {
        when(classesRepository.findById(classes.getId())).thenReturn(Optional.of(classes));
        when(professorRepository.findById(professor.getId())).thenReturn(Optional.of(professor));
        when(classesRepository.classTimeAlreadyInUse(
                classesDTO.getClassday(), classesDTO.getTime(), classesDTO.getClassRoom())).thenReturn(true);
        ResponseEntity<ClassesProfessorResponseDTO> responseEntity =
                classesService.putClass(classes.getId(), classesDTO);

        assertEquals(409, responseEntity.getStatusCode().value());
    }

    @DisplayName("Testa método putClass quando professor e classe não existem")
    @Test
    void When_putClass_Expect_404() {
        when(classesRepository.findById(classes.getId())).thenReturn(Optional.empty());
        when(professorRepository.findById(professor.getId())).thenReturn(Optional.empty());
        ResponseEntity<ClassesProfessorResponseDTO> responseEntity =
                classesService.putClass(classes.getId(), classesDTO);

        assertEquals(404, responseEntity.getStatusCode().value());
    }

    @DisplayName("Testa método deleteClass quando classe existe")
    @Test
    void When_deleteClass_Expect_200() {
        classes.setStudents(List.of(new Student(), new Student()));
        when(classesRepository.findById(classes.getId())).thenReturn(Optional.of(classes));
        ResponseEntity responseEntity = classesService.deleteClass(classes.getId());
        assertEquals(200, responseEntity.getStatusCode().value());
    }

    @DisplayName("Testa método deleteClass quando classe não existe")
    @Test
    void When_deleteClass_Expect_404() {
        when(classesRepository.findById(classes.getId())).thenReturn(Optional.empty());
        ResponseEntity responseEntity = classesService.deleteClass(classes.getId());
        assertEquals(404, responseEntity.getStatusCode().value());
    }

    @DisplayName("Testa método classDetails quando classe existe")
    @Test
    void When_classDetails_Expect_viewClassDetails() {
        Model model = mock(Model.class);
        when(classesRepository.findById(classes.getId())).thenReturn(Optional.of(classes));
        when(studentRepository.findAllByClassesId(classes.getId())).thenReturn(List.of(new Student()));
        ModelAndView modelAndView = classesService.classDetails(classes.getId(), model);
        assertEquals("classDetails", modelAndView.getViewName());
    }

    @DisplayName("Testa método classDetails quando classe não existe")
    @Test
    void When_classDetails_Expect_viewError() {
        Model model = mock(Model.class);
        when(classesRepository.findById(classes.getId())).thenReturn(Optional.empty());
        ModelAndView modelAndView = classesService.classDetails(classes.getId(), model);
        assertEquals("error", modelAndView.getViewName());
    }

    @DisplayName("Testa método addStudentToClass quando a classe existe, e estudante não existe na classe")
    @Test
    void When_addStudentToClass_Expect_200() {
        when(classesRepository.findById(classes.getId())).thenReturn(Optional.of(classes));
        when(classesRepository.studentExistInClass(student.getId(), classes.getId())).thenReturn(false);
        when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));
        ResponseEntity responseEntity = classesService.addStudentsToClass(classes.getId(), student.getId());
        assertEquals(200, responseEntity.getStatusCode().value());
    }

    @DisplayName("Testa método addStudentToClass quando a classe e estudante não existem")
    @Test
    void When_addStudentToClass_Expect_404() {
        when(classesRepository.findById(classes.getId())).thenReturn(Optional.empty());
        when(studentRepository.findById(student.getId())).thenReturn(Optional.empty());
        ResponseEntity responseEntity = classesService.addStudentsToClass(classes.getId(), student.getId());
        assertEquals(404, responseEntity.getStatusCode().value());
    }

    @DisplayName("Testa método addStudentToClass quando a classe existe, e estudante existe na classe")
    @Test
    void When_addStudentToClass_Expect_409() {
        when(classesRepository.findById(classes.getId())).thenReturn(Optional.of(classes));
        when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));
        when(classesRepository.studentExistInClass(student.getId(), classes.getId())).thenReturn(true);
        ResponseEntity responseEntity = classesService.addStudentsToClass(classes.getId(), student.getId());
        assertEquals(409, responseEntity.getStatusCode().value());
    }

    @DisplayName("Testa método searchStudent quando estudante com keyword e shift existe")
    @Test
    void When_searchStudent_Expect_200() {
        when(studentRepository.findByKeyword(2, "Manhã")).thenReturn(List.of(student));
        ResponseEntity<List<SearchDTO>> responseEntity = classesService.searchStudent(2, "08:00 - 11:40");
        assertEquals(200, responseEntity.getStatusCode().value());
    }

    @DisplayName("Testa método searchStudent quando estudante com keyword e shift não existe")
    @Test
    void When_searchStudent_Expect_404() {
        ResponseEntity<List<SearchDTO>> responseEntity = classesService.searchStudent(2, "Noite");
        assertEquals(404, responseEntity.getStatusCode().value());
    }

    @DisplayName("Testa método removeStudentOfClass quando estudante e classe existem")
    @Test
    void When_removeStudentOfClass_Expect_200() {
        when(classesRepository.findById(classes.getId())).thenReturn(Optional.of(classes));
        when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));
        ResponseEntity responseEntity = classesService.removeStudentOfClass(classes.getId(), student.getId());
        assertEquals(200, responseEntity.getStatusCode().value());
    }

    @DisplayName("Testa método removeStudentOfClass quando estudante e classe não existem")
    @Test
    void When_removeStudentOfClass_Expect_404() {
        when(classesRepository.findById(classes.getId())).thenReturn(Optional.empty());
        when(studentRepository.findById(student.getId())).thenReturn(Optional.empty());
        ResponseEntity responseEntity = classesService.removeStudentOfClass(classes.getId(), student.getId());
        assertEquals(404, responseEntity.getStatusCode().value());
    }
}

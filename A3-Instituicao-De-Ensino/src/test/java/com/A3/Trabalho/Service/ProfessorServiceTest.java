package com.A3.Trabalho.Service;

import com.A3.Trabalho.DTO.Professors.ProfessorDTO;
import com.A3.Trabalho.Model.Classes;
import com.A3.Trabalho.Model.Professor;
import com.A3.Trabalho.Repository.ClassesRepository;
import com.A3.Trabalho.Repository.ProfessorRepository;
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
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProfessorServiceTest {

    @Mock
    ProfessorRepository professorRepository;

    @Mock
    ClassesRepository classesRepository;

    @InjectMocks
    ProfessorService professorService;

    private Classes classes;
    private ProfessorDTO professorDTO;
    private Professor professor;

    @BeforeEach
    void setUp() {
        professorDTO = new ProfessorDTO("Ricardo", "ricardo@gmail.com", "11052654711", "Mestre", "1980-09-12");
        classes = new Classes();
        professor = professorDTO.DTOToProfessor();
        classes.setProfessor(professor);
    }

    @DisplayName("Testa método addProfessor quando cpf e email estão disponíveis")
    @Test
    void When_addProfessor_Expect_Status200() {

        when(professorRepository.emailExists(professorDTO.DTOToProfessor().getEmail())).thenReturn(false);
        when(professorRepository.cpfExists(professorDTO.DTOToProfessor().getCpf())).thenReturn(false);

        ResponseEntity<Map<String, Boolean>> responseEntity = professorService.addProfessor(professorDTO);

        assertEquals(200, responseEntity.getStatusCode().value());
    }

    @DisplayName("Testa método addProfessor quando cpf e email estão indisponíveis")
    @Test
    void When_addProfessor_Expect_Status409() {
        when(professorRepository.emailExists(professorDTO.DTOToProfessor().getEmail())).thenReturn(true);
        when(professorRepository.cpfExists(professorDTO.DTOToProfessor().getCpf())).thenReturn(true);

        ResponseEntity<Map<String, Boolean>> responseEntity = professorService.addProfessor(professorDTO);

        assertEquals(409, responseEntity.getStatusCode().value());
    }

    @DisplayName("Testa método professorDetails quando o professor existe")
    @Test
    void When_professorDetails_Expect_viewProfessorDetails() {
        List<Classes> classes1 = Arrays.asList(classes);
        Model model = mock(Model.class);

        when(classesRepository.findClassesByProfessorId(professor.getId())).thenReturn(classes1);
        when(professorRepository.findById(professor.getId())).thenReturn(Optional.of(professor));

        ModelAndView modelAndView = professorService.professorDetails(professor.getId(), model);

        assertEquals("professorDetails", modelAndView.getViewName());
    }

    @DisplayName("Testa método professorDetails quando o professor não existe")
    @Test
    void When_professorDetails_Expect_viewError() {
        Model model = mock(Model.class);

        when(classesRepository.findClassesByProfessorId(professor.getId())).thenReturn(List.of(classes));
        when(professorRepository.findById(professor.getId())).thenReturn(Optional.empty());

        ModelAndView modelAndView = professorService.professorDetails(professor.getId(), model);

        assertEquals("error", modelAndView.getViewName());
    }

    @DisplayName("Testa método getProfessor quando o professor existe")
    @Test
    void When_getProfessor_Expect_200() {
        when(professorRepository.findById(professor.getId())).thenReturn(Optional.of(professor));

        ResponseEntity<ProfessorDTO> responseEntity = professorService.getProfessor(professor.getId());
        assertEquals(200, responseEntity.getStatusCode().value());
    }

    @DisplayName("Testa método getProfessor quando o professor não existe")
    @Test
    void When_getProfessor_Expect_404() {
        when(professorRepository.findById(professor.getId())).thenReturn(Optional.empty());

        ResponseEntity<ProfessorDTO> responseEntity = professorService.getProfessor(professor.getId());
        assertEquals(404, responseEntity.getStatusCode().value());
    }

    @DisplayName("Testa método putProfessor quando o email e cpf estão disponíveis")
    @Test
    void When_putProfessor_Expect_200() {
        when(professorRepository.findById(professor.getId())).thenReturn(Optional.of(new Professor()));
        when(professorRepository.cpfExists(professorDTO.getCpf())).thenReturn(false);
        when(professorRepository.emailExists(professorDTO.getEmail())).thenReturn(false);

        ResponseEntity<Map<String, Boolean>> responseEntity = professorService.putProfessor(professor.getId(), professorDTO);

        assertEquals(200, responseEntity.getStatusCode().value());
    }

    @DisplayName("Testa método putProfessor quando o email e cpf estão indisponíveis")
    @Test
    void When_putProfessor_Expect_409() {
        when(professorRepository.findById(professor.getId())).thenReturn(Optional.of(new Professor()));
        when(professorRepository.cpfExists(professorDTO.getCpf())).thenReturn(true);
        when(professorRepository.emailExists(professorDTO.getEmail())).thenReturn(true);

        ResponseEntity<Map<String, Boolean>> responseEntity = professorService.putProfessor(professor.getId(), professorDTO);

        assertEquals(409, responseEntity.getStatusCode().value());
    }

    @DisplayName("Testa método putProfessor quando o professor não existe")
    @Test
    void When_putProfessor_Expect_404() {
        when(professorRepository.findById(professor.getId())).thenReturn(Optional.empty());
        ResponseEntity<Map<String, Boolean>> responseEntity = professorService.putProfessor(professor.getId(), professorDTO);

        assertEquals(404, responseEntity.getStatusCode().value());
    }

    @DisplayName("Testa método deleteProfessor quando o professor existe")
    @Test
    void When_deleteProfessor_Expect_200() {
        when(professorRepository.findById(professor.getId())).thenReturn(Optional.of(professor));
        ResponseEntity responseEntity = professorService.deleteProfessor(professor.getId());
        assertEquals(200, responseEntity.getStatusCode().value());
    }

    @DisplayName("Testa método deleteProfessor quando o professor não existe")
    @Test
    void When_deleteProfessor_Expect_409() {
        when(professorRepository.findById(professor.getId())).thenReturn(Optional.empty());
        ResponseEntity responseEntity = professorService.deleteProfessor(professor.getId());
        assertEquals(409, responseEntity.getStatusCode().value());
    }
}

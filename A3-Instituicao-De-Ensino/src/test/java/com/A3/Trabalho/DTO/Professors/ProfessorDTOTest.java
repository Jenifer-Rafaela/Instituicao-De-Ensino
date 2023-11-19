package com.A3.Trabalho.DTO.Professors;

import com.A3.Trabalho.Model.Professor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProfessorDTOTest {

    private ProfessorDTO professorDTO = new ProfessorDTO("Jenifer", "jenifer@gmail.com", "12345678910", "Mestre", "1980-05-21");
    private Professor professor1 = new Professor(2L, "Jenifer", "jenifer@gmail.com", "11052654711", "Mestre", Date.valueOf("1980-05-13"));

    @DisplayName("Testa método DTOToProfessor")
    @Test
    void DTOToProfessor() {
        Professor professor = professorDTO.DTOToProfessor();

        assertEquals(professor.getName(), professorDTO.getName());
        assertEquals(professor.getEmail(), professorDTO.getEmail());
        assertEquals(professor.getCpf(), professorDTO.getCpf());
        assertEquals(professor.getDegree(), professorDTO.getDegree());
        assertEquals(professor.getDate(), Date.valueOf(professorDTO.getDate()));
    }

    @DisplayName("Testa método professorToDTOList")
    @Test
    void DTOToProfessorUpdate() {
        Professor professor = professorDTO.DTOToProfessorUpdate(professor1);

        assertEquals(professor1.getName(), professor.getName());
        assertEquals(professor1.getEmail(), professor.getEmail());
        assertEquals(professor1.getCpf(), professor.getCpf());
        assertEquals(professor1.getDegree(), professor.getDegree());
        assertEquals(professor1.getDate(), professor.getDate());
    }
}

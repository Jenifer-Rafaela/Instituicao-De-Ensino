package com.A3.Trabalho.DTO.Classes;

import com.A3.Trabalho.Model.Classes;
import com.A3.Trabalho.Model.Professor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClassesProfessorResponseDTOTest {

    private ClassesProfessorResponseDTO classesProfessorResponseDTO = new ClassesProfessorResponseDTO();

    @DisplayName("Testa método classesToDTO")
    @Test
    void classesToDTO() {
        Professor professor = new Professor();
        professor.setName("Ricardo");
        professor.setDegree("Mestre");
        professor.setId(2L);
        Classes classes = new Classes("Sistemas","Sala 2","14:40 - 16:00","Terças e Quintas",professor);

        assertEquals(classes.getClassName(),classesProfessorResponseDTO.classesToDTO(classes).getClassName());
        assertEquals(classes.getClassRoom(),classesProfessorResponseDTO.classesToDTO(classes).getClassRoom());
        assertEquals(classes.getTime(),classesProfessorResponseDTO.classesToDTO(classes).getTime());
        assertEquals(classes.getClassday(),classesProfessorResponseDTO.classesToDTO(classes).getClassday());
        assertEquals(classes.getProfessor().getId(),classesProfessorResponseDTO.classesToDTO(classes).getProfessorId());
        assertEquals(classes.getProfessor().getName(),classesProfessorResponseDTO.classesToDTO(classes).getProfessorName());
        assertEquals(classes.getProfessor().getDegree(),classesProfessorResponseDTO.classesToDTO(classes).getProfessorDegree());
    }
}

package com.A3.Trabalho.DTO.Classes;

import com.A3.Trabalho.Model.Classes;
import com.A3.Trabalho.Model.Professor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClassesResponseDTOTest {

    private ClassesResponseDTO classesResponseDTO = new ClassesResponseDTO();

    @DisplayName("Testa método classesToDTO")
    @Test
    void classesToDTO() {
        Classes classes = new Classes("Sistemas", "Sala 2", "14:40 - 16:00", "Terças e Quintas", new Professor());

        assertEquals(classes.getId(), classesResponseDTO.classesToDTO(classes).getId());
        assertEquals(classes.getClassName(), classesResponseDTO.classesToDTO(classes).getClassName());
        assertEquals(classes.getClassRoom(), classesResponseDTO.classesToDTO(classes).getClassRoom());
        assertEquals(classes.getTime(), classesResponseDTO.classesToDTO(classes).getTime());
        assertEquals(classes.getClassday(), classesResponseDTO.classesToDTO(classes).getClassday());
    }

    @DisplayName("Testa método classesToDTOList")
    @Test
    void classesToDTOList() {
        Classes classes = new Classes("Sistemas", "Sala 2", "14:40 - 16:00", "Terças e Quintas", new Professor());
        Classes classes1 = new Classes("Segurança", "Sala 3", "19:40 - 22:00", "Terças e Sextas", new Professor());

        List<ClassesResponseDTO> ClassesResponseDTOList = classesResponseDTO.classesToDTOList(List.of(classes, classes1));

        assertEquals(classes.getId(), ClassesResponseDTOList.get(0).getId());
        assertEquals(classes.getClassName(), ClassesResponseDTOList.get(0).getClassName());
        assertEquals(classes.getClassRoom(), ClassesResponseDTOList.get(0).getClassRoom());
        assertEquals(classes.getTime(), ClassesResponseDTOList.get(0).getTime());
        assertEquals(classes.getClassday(), ClassesResponseDTOList.get(0).getClassday());

        assertEquals(classes1.getId(), ClassesResponseDTOList.get(1).getId());
        assertEquals(classes.getClassName(), ClassesResponseDTOList.get(0).getClassName());
        assertEquals(classes1.getClassRoom(), ClassesResponseDTOList.get(1).getClassRoom());
        assertEquals(classes1.getTime(), ClassesResponseDTOList.get(1).getTime());
        assertEquals(classes1.getClassday(), ClassesResponseDTOList.get(1).getClassday());
    }
}

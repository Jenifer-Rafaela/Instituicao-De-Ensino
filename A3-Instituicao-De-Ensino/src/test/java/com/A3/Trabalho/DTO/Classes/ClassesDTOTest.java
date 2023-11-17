package com.A3.Trabalho.DTO.Classes;

import com.A3.Trabalho.Model.Classes;
import com.A3.Trabalho.Model.Professor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClassesDTOTest {

    private ClassesDTO classesDTO = new ClassesDTO("Segurança", "Sala 5", "08:40 - 11:40", "Segundas e Sextas", "1");

    @DisplayName("Testa método DTOToClass")
    @Test
    void DTOToClass() {
        Classes classes = classesDTO.DTOToClass(new Professor());

        assertEquals(classes.getClassName(), classesDTO.DTOToClass(new Professor()).getClassName());
        assertEquals(classes.getClassRoom(), classesDTO.DTOToClass(new Professor()).getClassRoom());
        assertEquals(classes.getTime(), classesDTO.DTOToClass(new Professor()).getTime());
        assertEquals(classes.getClassday(), classesDTO.DTOToClass(new Professor()).getClassday());
        assertEquals(classes.getProfessor().getId(), classesDTO.DTOToClass(new Professor()).getProfessor().getId());
    }

    @DisplayName("Testa método classesToDTOList")
    @Test
    void classesToDTOList() {
        Classes classes = classesDTO.DTOToClass(new Professor());
        Classes classes1 = new Classes("Sistemas", "Sala 2", "14:40 - 16:00", "Terças e Quintas", new Professor());

        List<ClassesDTO> classesDTOList = classesDTO.classesToDTOList(List.of(classes, classes1));

        assertEquals(classes.getClassName(), classesDTOList.get(0).getClassName());
        assertEquals(classes.getClassRoom(), classesDTOList.get(0).getClassRoom());
        assertEquals(classes.getTime(), classesDTOList.get(0).getTime());
        assertEquals(classes.getClassday(), classesDTOList.get(0).getClassday());
        assertEquals(classes.getProfessor().getId(), classesDTOList.get(0).getProfessor());

        assertEquals(classes1.getClassName(), classesDTOList.get(1).getClassName());
        assertEquals(classes1.getClassRoom(), classesDTOList.get(1).getClassRoom());
        assertEquals(classes1.getTime(), classesDTOList.get(1).getTime());
        assertEquals(classes1.getClassday(), classesDTOList.get(1).getClassday());
        assertEquals(classes1.getProfessor().getId(), classesDTOList.get(1).getProfessor());
    }

    @DisplayName("Testa método classesToDTO")
    @Test
    void classesToDTO() {
        Classes classes = classesDTO.DTOToClass(new Professor());

        ClassesDTO classesDTO1 = classesDTO.classesToDTO(classes);

        assertEquals(classesDTO1.getClassName(), classes.getClassName());
        assertEquals(classesDTO1.getClassRoom(), classes.getClassRoom());
        assertEquals(classesDTO1.getClassday(), classes.getClassday());
        assertEquals(classesDTO1.getProfessor(), classes.getProfessor().getId());
        assertEquals(classesDTO1.getTime(), classes.getTime());
    }

    @DisplayName("Testa método DTOToClassUpdate")
    @Test
    void DTOToClassUpdate() {
        Professor professor = new Professor();
        professor.setName("Jenifer");
        Classes classes = new Classes("Turma", "Sala 1", "08:40 - 11:40", "Quartas e Quinta", professor);

        Classes classes1 = classesDTO.DTOToClassUpdate(classes, professor);

        assertEquals(classes1.getClassName(), classes.getClassName());
        assertEquals(classes1.getClassRoom(), classes.getClassRoom());
        assertEquals(classes1.getClassday(), classes.getClassday());
        assertEquals(classes1.getProfessor().getName(), classes.getProfessor().getName());
        assertEquals(classes1.getTime(), classes.getTime());
    }
}

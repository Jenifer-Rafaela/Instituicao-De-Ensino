package com.A3.Trabalho.DTO.Students;

import com.A3.Trabalho.Model.Student;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudentDTOTest {

    private StudentDTO studentDTO = new StudentDTO("Jenifer", "jenifer@gmail.com", "12345678910", "Manhã", "2000-05-13");
    private Student student = new Student("Rafaela", "rafaela@gmail.com", "01987654321", "Noite", Date.valueOf("2000-01-01"));

    @DisplayName("Testa método DTOToStudent")
    @Test
    void DTOToStudent() {
        Student student = studentDTO.DTOToStudent();

        assertEquals(student.getName(), studentDTO.getName());
        assertEquals(student.getEmail(), studentDTO.getEmail());
        assertEquals(student.getCpf(), studentDTO.getCpf());
        assertEquals(student.getShift(), studentDTO.getShift());
        assertEquals(student.getDate(), Date.valueOf(studentDTO.getDate()));
    }

    @DisplayName("Testa método DTOToStudentUpdate")
    @Test
    void DTOToStudentUpdate() {
        Student student1 = studentDTO.DTOToStudentUpdate(student);

        assertEquals(student1.getName(), studentDTO.getName());
        assertEquals(student1.getEmail(), studentDTO.getEmail());
        assertEquals(student1.getCpf(), studentDTO.getCpf());
        assertEquals(student1.getShift(), studentDTO.getShift());
        assertEquals(student1.getDate(), Date.valueOf(studentDTO.getDate()));
    }

}

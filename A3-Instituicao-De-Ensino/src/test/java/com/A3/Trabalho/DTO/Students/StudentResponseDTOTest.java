package com.A3.Trabalho.DTO.Students;

import com.A3.Trabalho.Model.Student;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudentResponseDTOTest {

    private Student student = new Student("Rafaela", "rafaela@gmail.com", "01987654321", "Noite", Date.valueOf("2000-01-01"));
    private Student student2 = new Student("Jenifer", "jenifer@gmail.com", "12345678910", "Manhã", Date.valueOf("2000-05-13"));
    private StudentResponseDTO studentResponseDTO = new StudentResponseDTO();

    @DisplayName("Testa método studentToDTOList")
    @Test
    void studentToDTOList() {
        List<StudentResponseDTO> srList = studentResponseDTO.studentToDTOList(List.of(student, student2));
        String cpf = "019.876.543-21";
        String cpf2 = "123.456.789-10";
        String date = "01/01/2000";
        String date2 = "13/05/2000";

        assertEquals(student.getId(), srList.get(0).getId());
        assertEquals(student.getName(), srList.get(0).getName());
        assertEquals(student.getEmail(), srList.get(0).getEmail());
        assertEquals(cpf, srList.get(0).getCpf());
        assertEquals(student.getShift(), srList.get(0).getShift());
        assertEquals(date, srList.get(0).getDate());

        assertEquals(student2.getId(), srList.get(1).getId());
        assertEquals(student2.getName(), srList.get(1).getName());
        assertEquals(student2.getEmail(), srList.get(1).getEmail());
        assertEquals(cpf2, srList.get(1).getCpf());
        assertEquals(student2.getShift(), srList.get(1).getShift());
        assertEquals(date2, srList.get(1).getDate());
    }

    @DisplayName("Testa método studentToDTO")
    @Test
    void studentToDTO() {
        StudentResponseDTO studentResponseDTO1 = studentResponseDTO.studentToDTO(student);
        String cpf = "019.876.543-21";
        String date = "01/01/2000";
        assertEquals(student.getId(), studentResponseDTO1.getId());
        assertEquals(student.getName(), studentResponseDTO1.getName());
        assertEquals(student.getEmail(), studentResponseDTO1.getEmail());
        assertEquals(cpf, studentResponseDTO1.getCpf());
        assertEquals(student.getShift(), studentResponseDTO1.getShift());
        assertEquals(date, studentResponseDTO1.getDate());
    }
}

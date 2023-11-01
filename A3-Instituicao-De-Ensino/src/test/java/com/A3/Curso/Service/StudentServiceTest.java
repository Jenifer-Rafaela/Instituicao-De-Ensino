package com.A3.Curso.Service;

import com.A3.Curso.DTO.Students.StudentDTO;
import com.A3.Curso.Model.Student;
import com.A3.Curso.Repository.ClassesRepository;
import com.A3.Curso.Repository.StudentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private ClassesRepository classRepository;

    @InjectMocks
    private StudentService studentService;

    @Test
    @DisplayName("Salvar quando já existe aluno com o mesmo email cadastrado")
    void salvarQuandoJaExisteAlunoCadastrado() {
        StudentDTO studentDTO = new StudentDTO();

        studentDTO.setName("Jenifer");
        studentDTO.setEmail("jenifer@gmail.com");
        studentDTO.setCpf("11052654711");
        studentDTO.setShift("Noite");
        studentDTO.setDate(Date.valueOf("2000-05-13"));

        String studentResult = studentRepository.emailExists(studentDTO.getEmail(),0).get();
        Mockito.when(!studentResult.isEmpty()).thenReturn(true);
    }
}
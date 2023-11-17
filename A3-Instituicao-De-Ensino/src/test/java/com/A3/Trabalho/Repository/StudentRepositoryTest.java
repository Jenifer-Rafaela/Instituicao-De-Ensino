package com.A3.Trabalho.Repository;

import com.A3.Trabalho.Model.Student;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Date;
import java.util.List;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    Student student1;
    Student student2;

    @BeforeEach
    void setUp() {
        student1 = new Student("Jenifer", "jenifer@gmail.com", "11052654711", "Noite", Date.valueOf("2000-05-13"));
        student2 = new Student("João", "joão@gmail.com", "17345656756", "Manhã", Date.valueOf("1995-01-02"));
        studentRepository.saveAll(List.of(student1, student2));
        System.out.println(studentRepository.findAll());
    }

    @DisplayName("Testa método emailExists quando email existe no banco de dados")
    @Test
    void When_emailExists_Expect_true() {
        boolean emailExists = studentRepository.emailExists("jenifer@gmail.com");
        Assertions.assertThat(emailExists).isTrue();
    }

    @DisplayName("Testa método emailExists quando email não existe no banco de dados")
    @Test
    void When_emailExists_Expect_false() {
        boolean emailExists = studentRepository.emailExists("ricardo@gmail.com");
        Assertions.assertThat(emailExists).isFalse();
    }

    @DisplayName("Testa método cpfExists quando cpf existe no banco de dados")
    @Test
    void When_CpfExists_Expect_true() {
        boolean cpfExists = studentRepository.cpfExists("17345656756");
        Assertions.assertThat(cpfExists).isTrue();
    }

    @DisplayName("Testa método cpfExists quando cpf não existe no banco de dados")
    @Test
    void When_cpfExists_Expect_false() {
        boolean cpfExists = studentRepository.cpfExists("03515609040");
        Assertions.assertThat(cpfExists).isFalse();
    }

    @DisplayName("Testa se keyword e turno existem")
    @Test
    void When_findByKeyword_Expect_isNotEmpty() {
        List<Student> student = studentRepository.findByKeyword(student1.getId(), "Noite");
        Assertions.assertThat(student).isNotEmpty();
    }

    @DisplayName("Testa se keyword e turno não existem")
    @Test
    void When_findByKeyword_Expect_isEmpty() {
        List<Student> student = studentRepository.findByKeyword(student2.getId(), "Noite");
        Assertions.assertThat(student).isEmpty();
    }
}
package com.A3.Trabalho.Repository;

import com.A3.Trabalho.Model.Classes;
import com.A3.Trabalho.Model.Professor;
import com.A3.Trabalho.Model.Student;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.sql.Date;

@Transactional
@DataJpaTest
class ClassesRepositoryTest {

    @Autowired
    private ClassesRepository classesRepository;
    @Autowired
    private ProfessorRepository professorRepository;
    @Autowired
    TestEntityManager entityManager;

    private Student student1;
    private Classes classes1;

    @Autowired
    private StudentRepository studentRepository;

    @BeforeEach
    void setUp() {
        Professor professor = new Professor("Professor", "professor@gmail.com", "84892340782", "Mestre", Date.valueOf("1992-04-10"));
        professorRepository.save(professor);

        student1 = new Student("Jenifer", "jenifer@gmail.com", "17345656756", "Noite", Date.valueOf("2000-05-13"));
        studentRepository.save(student1);

        classes1 = new Classes("UC", "Sala 5", "08:40 - 11:40", "Segundas e Sextas", professor);
        classesRepository.save(classes1);
        System.out.println(student1.getId());
        classesRepository.addStudentToClass( student1.getId(), classes1.getId());
    }

    @Transactional
    @DisplayName("Testa método studentExistInClass quando estudante existe na turma")
    @Test
    void When_studentExistsInClass_Expect_true() {
        System.out.println(student1.getId());
        boolean studentExists = classesRepository.studentExistInClass(student1.getId(), classes1.getId());
        Assertions.assertThat(studentExists).isTrue();
    }

    @DisplayName("Testa método studentExistInClass quando o estudante naõ existe na turma")
    @Test
    void When_studentExistInClass_Expect_false() {
        Student student3 = new Student(1005L, "Rafaela", "rafaela@gmail.com", "17345656756", "Manhã", Date.valueOf("2000-05-13"));
        boolean studentExist = classesRepository.studentExistInClass(student3.getId(), classes1.getId());
        Assertions.assertThat(studentExist).isFalse();
    }

    @DisplayName("Testa método removeAllStudentsFromClass quando remoção é feita com sucesso")
    @Test
    void When_removeAllStudentsFromClass_Expect_true() {
        Classes classes = classesRepository.findById(classes1.getId()).get();
        classesRepository.removeAllStudentsFromClass(classes.getId());
        boolean studentExists = classesRepository.studentExistInClass(student1.getId(), classes.getId());
        Assertions.assertThat(!studentExists).isTrue();
    }

    @DisplayName("Testa método removeStudentFromClass quando remoção é feita com sucesso")
    @Test
    void When_RemoveStudentFromClass_Expect_true() {
        classesRepository.removeStudentFromClass(student1.getId(), classes1.getId());
        boolean studentExists = classesRepository.studentExistInClass(student1.getId(), classes1.getId());
        Assertions.assertThat(!studentExists).isTrue();
    }

    @DisplayName("Testa método classTimeAlreadyInUse quando horário, dia e sala estão em uso")
    @Test
    void When_classTimeAlreadyInUse_Expect_true() {
        boolean exists = classesRepository.classTimeAlreadyInUse(classes1.getClassday(), classes1.getTime(), classes1.getClassRoom());
        Assertions.assertThat(exists).isTrue();
    }

    @DisplayName("Testa método classTimeAlreadyInUse quando horário, dia e sala não estão em uso")
    @Test
    void When_classTimeAlreadyInUse_Expect_false() {
        boolean exists = classesRepository.classTimeAlreadyInUse(classes1.getClassday(), classes1.getTime(), "Sala 2");
        Assertions.assertThat(exists).isFalse();
    }
}
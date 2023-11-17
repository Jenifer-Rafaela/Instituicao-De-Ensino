package com.A3.Trabalho.Repository;

import com.A3.Trabalho.Model.Professor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Date;
import java.util.List;

@DataJpaTest
class ProfessorRepositoryTest {

    @Autowired
    private ProfessorRepository professorRepository;

    @BeforeEach
    void setUp() {
        Professor professor1 = new Professor("Jenifer", "jenifer@gmail.com", "11052654711", "Mestre", Date.valueOf("1980-05-13"));
        Professor professor2 = new Professor("João", "joão@gmail.com", "17345656756", "Mestre", Date.valueOf("1982-01-02"));
        Professor professor3 = new Professor("João", "joãoL@gmail.com", "1234567891", "Mestre", Date.valueOf("1981-01-02"));
        professorRepository.saveAll(List.of(professor1, professor2, professor3));
    }

    @DisplayName("Testa método emailExists quando email existe no banco de dados")
    @Test
    void when_emailExists_Expect_true() {
        boolean emailExists = professorRepository.emailExists("jenifer@gmail.com");
        Assertions.assertThat(emailExists).isTrue();
    }

    @DisplayName("Testa método emailExists quando email não existe no banco de dados")
    @Test
    void when_emailExists_Expect_false() {
        boolean emailExists = professorRepository.emailExists("ricardo@gmail.com");
        Assertions.assertThat(emailExists).isFalse();
    }

    @DisplayName("Testa método cpfExists quando CPF existe no banco de dados")
    @Test
    void when_cpfExists_Expect_true() {
        Assertions.assertThat(professorRepository.cpfExists("17345656756")).isTrue();
    }

    @DisplayName("Testa método cpfExists quando CPF não existe no banco de dados")
    @Test
    void when_cpfExists_Expect_false() {
        Assertions.assertThat(professorRepository.cpfExists("03515609040")).isFalse();
    }
}
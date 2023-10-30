package com.A3.Curso.Repository;

import com.A3.Curso.Model.Professor;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Transactional
@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {

    @Query(value = "SELECT email FROM PROFESSOR\n" +
            "WHERE UPPER(EMAIL) = UPPER(:email) AND ID != :professorId\n" +
            "UNION\n" +
            "SELECT email FROM STUDENT\n" +
            "WHERE UPPER(EMAIL) = UPPER(:email)\n" +
            "ORDER BY EMAIL;", nativeQuery = true)
    Optional<String> emailExists(@Param("email") String email, @Param("professorId") long id);

    @Query(value = "SELECT CPF FROM PROFESSOR\n" +
            "WHERE CPF = :cpf AND ID != :professorId\n" +
            "UNION ALL\n" +
            "SELECT CPF FROM STUDENT\n" +
            " WHERE CPF = :cpf\n" +
            "ORDER BY CPF;", nativeQuery = true)
    Optional<String> cpfExists(@Param("cpf") String cpf, @Param("professorId") long id);
}

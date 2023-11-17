package com.A3.Trabalho.Repository;

import com.A3.Trabalho.Model.Professor;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {

    @Query(value = "SELECT CASE WHEN\n" +
            "EXISTS (SELECT 1 FROM Student WHERE UPPER(EMAIL) = UPPER(:email))\n" +
            "OR\n" +
            "EXISTS (SELECT 1 FROM Professor WHERE UPPER(EMAIL) = UPPER(:email))\n" +
            "THEN TRUE\n" +
            "ELSE FALSE END;", nativeQuery = true)
    Boolean emailExists(@Param("email") String email);

    @Query(value = "SELECT CASE WHEN\n" +
            "EXISTS (SELECT 1 FROM Student WHERE CPF = :cpf)\n" +
            "OR\n" +
            "EXISTS (SELECT 1 FROM Professor WHERE CPF = :cpf)\n" +
            "THEN TRUE\n" +
            "ELSE FALSE END;", nativeQuery = true)
    Boolean cpfExists(@Param("cpf") String cpf);
}

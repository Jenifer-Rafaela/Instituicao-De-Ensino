package com.A3.Curso.Repository;

import com.A3.Curso.Model.Student;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findAllByClassesId(long classesId);

    @Query(value ="SELECT email FROM STUDENT\n" +
            "WHERE UPPER(EMAIL) = UPPER(:email) AND ID != :studentId\n" +
            "UNION\n" +
            "SELECT email FROM PROFESSOR\n" +
            "WHERE UPPER(EMAIL) = UPPER(:email)\n" +
            "ORDER BY EMAIL;", nativeQuery = true)
    Optional<String> emailExists(@Param("email")String email, @Param("studentId")long id);

    @Query(value ="SELECT CPF FROM STUDENT\n" +
            "WHERE CPF = :cpf AND ID != :studentId\n" +
            "UNION ALL\n" +
            "SELECT CPF FROM PROFESSOR\n" +
            "           WHERE CPF = :cpf\n" +
            "ORDER BY CPF;", nativeQuery = true)
    Optional<String> cpfExists(@Param("cpf")String cpf, @Param("studentId")long id);

    @Query(value = "SELECT * FROM student s WHERE s.id LIKE %:keyword% AND s.SHIFT = :shift", nativeQuery = true)
    List<Student> findByKeyword(@Param("keyword") long keyword, @Param("shift") String shift);

}

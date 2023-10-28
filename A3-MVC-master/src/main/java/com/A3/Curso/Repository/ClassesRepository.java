package com.A3.Curso.Repository;

import com.A3.Curso.Model.Classes;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Transactional
@Repository
public interface ClassesRepository extends JpaRepository<Classes, Long> {

    List<Classes> findClassesByProfessorId(long professorId);

    List<Classes> findClassesByStudentsId(long studentsId);

    @Query(value = "SELECT CS.CLASSES_ID FROM CLASSES_STUDENTS CS WHERE CS.STUDENTS_ID = :studentId AND CS.CLASSES_ID = :classId", nativeQuery = true)
    Long studentExistInClass(@Param("studentId") long studentId, @Param("classId") long classId);

    @Modifying
    @Query(value = "DELETE FROM CLASSES_STUDENTS WHERE CLASSES_ID = :classId", nativeQuery = true)
    void removeAllStudentsFromClass(@Param("classId") long classId);

    @Modifying
    @Query(value = "DELETE FROM CLASSES_STUDENTS s WHERE s.STUDENTS_ID = :studentId AND s.CLASSES_ID = :classId", nativeQuery = true)
    void removeStudentFromClass(@Param("studentId") long studentId, @Param("classId") long classId);

}

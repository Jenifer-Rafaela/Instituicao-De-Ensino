package com.A3.Trabalho.Repository;

import com.A3.Trabalho.Model.Classes;
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

    @Modifying
    @Query(value = "INSERT INTO CLASSES_STUDENTS (CLASSES_ID, STUDENTS_ID) VALUES (:classId, :studentId)", nativeQuery = true)
    void addStudentToClass(@Param("studentId") long studentId, @Param("classId") long classId);

    @Query(value = "SELECT CASE WHEN COUNT(CLASSES_STUDENTS.CLASSES_ID) > 0 THEN TRUE ELSE FALSE END\n" +
            "FROM CLASSES_STUDENTS\n" +
            "WHERE CLASSES_STUDENTS.CLASSES_ID = :classId AND CLASSES_STUDENTS.STUDENTS_ID = :studentId", nativeQuery = true)
    Boolean studentExistInClass(@Param("studentId") long studentId, @Param("classId") long classId);

    @Query(value = "SELECT CASE WHEN COUNT(CLASSES.ID) > 0 THEN TRUE ELSE FALSE END\n" +
            "FROM CLASSES\n" +
            "WHERE CLASSES.TIME = :classTime AND CLASSES.CLASS_ROOM = :classRoom AND CLASSES.CLASSDAY = :classDay", nativeQuery = true)
    Boolean classTimeAlreadyInUse(@Param("classDay") String classDay, @Param("classTime") String classTime, @Param("classRoom") String classRoom);


    @Modifying
    @Query(value = "DELETE FROM CLASSES_STUDENTS WHERE CLASSES_ID = :classId", nativeQuery = true)
    void removeAllStudentsFromClass(@Param("classId") long classId);

    @Modifying
    @Query(value = "DELETE FROM CLASSES_STUDENTS s WHERE s.STUDENTS_ID = :studentId AND s.CLASSES_ID = :classId", nativeQuery = true)
    void removeStudentFromClass(@Param("studentId") long studentId, @Param("classId") long classId);

}

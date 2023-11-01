package com.A3.Curso.DTO.Students;

import com.A3.Curso.Model.Student;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@NoArgsConstructor
@Getter
@Setter

/**
 * DTO para receber informações da view (students.html).
 * */
public class StudentDTO {

    private String name;
    private String email;
    private String cpf;
    private String shift;
    private Date date;

    /**
     * <strong>Método para transformar StudentDTO em Student.</strong>
     *
     * @return Student.
     */
    public Student DTOToStudent() {
        return new Student(name, email, cpf, shift, date);
    }

    /**
     * <strong>Método para transformar StudentDTO em Student.</strong>
     *
     * @param student receberá as informações de StudentDTO.
     * @return Student.
     */
    public Student DTOToStudentUpdate(Student student) {
        return new Student(student.getId(), name, email, cpf, shift, date);
    }
}

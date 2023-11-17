package com.A3.Trabalho.DTO.Students;

import com.A3.Trabalho.Model.Student;
import lombok.*;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
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

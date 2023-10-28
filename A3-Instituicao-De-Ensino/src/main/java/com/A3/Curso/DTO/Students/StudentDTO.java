package com.A3.Curso.DTO.Students;

import com.A3.Curso.Model.Student;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@NoArgsConstructor
@Getter
@Setter
public class StudentDTO {

    private String name;
    private String email;
    private String cpf;
    private String shift;
    private Date date;

//    private StudentDTO(String name, String email, String cpf, String shift, Date date) {
//        this.name = name;
//        this.email = email;
//        this.cpf = cpf;
//        this.shift = shift;
//        this.date = date;
//    }

    public Student DTOToStudent() {

        return new Student(name, email, cpf, shift, date);
    }

    public Student DTOToStudentUpdate(Student student) {
        return new Student(student.getId(), name, email, cpf, shift, date);
    }
}

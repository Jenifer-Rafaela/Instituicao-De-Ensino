package com.A3.Curso.DTO.Students;

import com.A3.Curso.Model.Student;
import lombok.*;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@Setter
public class StudentResponseDTO {

    private long id;
    private String name;
    private String email;
    private String cpf;
    private String shift;
    private Date date;

    public StudentResponseDTO studentsToDTO(Student student){
        return new StudentResponseDTO(student.getId(),student.getName(),student.getEmail() ,student.getCpf(),student.getShift(),student.getDate());
    }

    public List<StudentResponseDTO> studentsToDTOList(List<Student>  student){
        List<StudentResponseDTO> srdList = student.stream()
                .map(Student -> new StudentResponseDTO(
                        Student.getId(),
                        Student.getName(),
                        Student.getEmail(),
                        Student.getCpf(),
                        Student.getShift(),
                        Student.getDate()))
                .collect(Collectors.toList());
        return srdList;
    }
}

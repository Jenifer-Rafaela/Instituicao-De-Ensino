package com.A3.Trabalho.DTO.Students;

import com.A3.Trabalho.Model.Student;
import lombok.*;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@Setter
@ToString
/**
 * DTO para mostrar informações específicas de Student.
 * */
public class StudentResponseDTO {

    private long id;
    private String name;
    private String email;
    private String cpf;
    private String shift;
    private String date;

    /**
     * <strong>Método para transformar student em StudentResponseDTO.</strong>
     *
     * @param student será transformado em StudentResponseDTO.
     * @return StudentResponseDTO.
     */
    public StudentResponseDTO studentToDTO(Student student){
        return new StudentResponseDTO(student.getId(),student.getName(),student.getEmail() ,formatCpf(student.getCpf()),student.getShift(),formatDate(student.getDate()));
    }

    /**
     * <strong>Método para transformar uma lista de student em uma lista StudentResponseDTO.</strong>
     * @param student será transformado em uma lista StudentResponseDTO.
     * @return StudentResponseDTO.
     * */
    public List<StudentResponseDTO> studentToDTOList(List<Student>  student){
        List<StudentResponseDTO> srdList = student.stream()
                .map(Student -> new StudentResponseDTO(
                        Student.getId(),
                        Student.getName(),
                        Student.getEmail(),
                        formatCpf(Student.getCpf()),
                        Student.getShift(),
                        formatDate(Student.getDate())))
                .collect(Collectors.toList());
        return srdList;
    }

    private String formatDate(Date date){
        String dateArray[] = date.toString().split("-");
        return dateArray[2] + "/" + dateArray[1] + "/" + dateArray[0];
    }

    private String formatCpf(String cpf){
        return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }
}

package com.A3.Curso.DTO.Professors;

import com.A3.Curso.Model.Professor;
import lombok.*;

import java.sql.Date;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@Setter

/**
 * DTO para receber informações da view (professor.html).
 * */
public class ProfessorDTO {

    private String name;
    private String email;
    private String cpf;
    private String degree;
    private Date date;


    /**
     * <strong>Método para transformar ProfessorDTO em Professor.</strong>
     *
     * @return Professor.
     */
    public Professor DTOToProfessor() {
        return new Professor(name, email, cpf, degree, date);
    }

    /**
     * <strong>Método para transformar ProfessorDTO em Professor.</strong>
     *
     * @param professor receberá as informações de ProfessorDTO.
     * @return professor.
     */
    public Professor DTOToProfessorUpdate(Professor professor) {
        professor.setName(name);
        professor.setEmail(email);
        professor.setCpf(cpf);
        professor.setDegree(degree);
        professor.setDate(date);
        return professor;
    }

    /**
     * <strong>Método para transformar professor em ProfessorDTO.</strong>
     *
     * @param professor será transformado em ProfessorDTO.
     * @return ProfessorDTO.
     */
    public ProfessorDTO ProfessorToDTO(Professor professor) {
        return new ProfessorDTO(professor.getName(), professor.getEmail(), professor.getCpf(), professor.getDegree(), professor.getDate());
    }

}

package com.A3.Curso.DTO.Professors;

import com.A3.Curso.Model.Professor;
import lombok.*;

import java.sql.Date;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@Setter
public class ProfessorDTO {

    private String name;
    private String email;
    private String cpf;
    private String degree;
    private Date date;

    //Transforma DTO em Professor
    public Professor DTOToProfessor() {
        return new Professor(name, email, cpf, degree, date);
    }

    //Atualiza um professor
    public Professor DTOToProfessorUpdate(Professor professor) {
        return new Professor(professor.getId(), name, email, cpf, degree, date);
    }

    //Transforma professor em DTO
    public ProfessorDTO ProfessorToDTO(Professor professor) {
        return new ProfessorDTO(professor.getName(), professor.getEmail(), professor.getCpf(), professor.getDegree(), professor.getDate());
    }

}

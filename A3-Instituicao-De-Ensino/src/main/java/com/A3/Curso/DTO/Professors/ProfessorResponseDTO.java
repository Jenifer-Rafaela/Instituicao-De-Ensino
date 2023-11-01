package com.A3.Curso.DTO.Professors;

import com.A3.Curso.Model.Professor;
import lombok.*;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
/**
 * DTO para mostrar informações específicas do professor.
 * */
public class ProfessorResponseDTO {

    private long id;
    private String name;
    private String email;
    private String cpf;
    private String degree;
    private Date date;


    /**
     * <strong>Método para transformar uma lista de professor em uma lista ProfessorResponseDTO.</strong>
     * @param professor será transformado em uma lista ProfessorResponseDTO.
     * @return ProfessorResponseDTO.
     * */
    public List<ProfessorResponseDTO> professorToDTOList(List<Professor> professor) {
        List<ProfessorResponseDTO> prdList = professor.stream()
                .map(Professor -> new ProfessorResponseDTO(
                        Professor.getId(),
                        Professor.getName(),
                        Professor.getEmail(),
                        Professor.getCpf(),
                        Professor.getDegree(),
                        Professor.getDate()))
                .collect(Collectors.toList());

        return prdList;
    }
}

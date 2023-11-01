package com.A3.Curso.DTO.Professors;

import com.A3.Curso.Model.Professor;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
/**
 * DTO para mostrar informações específicas de professor
 * */
public class ProfessorClassResponseDTO {

    private long id;
    private String name;
    private String email;
    private String degree;

    /**
     * <strong>Método para transformar uma lista de professor em uma lista ProfessorClassResponseDTO.</strong>
     *
     * @param professor será transformado em uma lista ProfessorClassResponseDTO.
     * @return ProfessorClassResponseDTO.
     */
    public List<ProfessorClassResponseDTO> professorToDTOList(List<Professor> professor) {
        List<ProfessorClassResponseDTO> prdList = professor.stream()
                .map(Professor -> new ProfessorClassResponseDTO(
                        Professor.getId(),
                        Professor.getName(),
                        Professor.getEmail(),
                        Professor.getDegree()))
                .collect(Collectors.toList());

        return prdList;
    }
}

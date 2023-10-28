package com.A3.Curso.DTO.Professors;

import com.A3.Curso.Model.Professor;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class ProfessorClassResponseDTO {

    private long id;
    private String name;
    private String email;
    private String degree;

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

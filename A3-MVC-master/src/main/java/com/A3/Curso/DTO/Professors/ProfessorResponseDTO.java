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
public class ProfessorResponseDTO {

    private long id;
    private String name;
    private String email;
    private String cpf;
    private String degree;
    private Date date;

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

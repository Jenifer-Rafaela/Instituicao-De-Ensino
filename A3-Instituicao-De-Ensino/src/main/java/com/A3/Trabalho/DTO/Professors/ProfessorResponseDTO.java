package com.A3.Trabalho.DTO.Professors;

import com.A3.Trabalho.DTO.Students.StudentResponseDTO;
import com.A3.Trabalho.Model.Professor;
import com.A3.Trabalho.Model.Student;
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
    private String date;

    /**
     * <strong>Método para transformar professor em ProfessorResponseDTO.</strong>
     *
     * @param professor será transformado em ProfessorResponseDTO.
     * @return ProfessorResponseDTO.
     */
    public ProfessorResponseDTO professorToDTO(Professor professor){
        return new ProfessorResponseDTO(professor.getId(),professor.getName(),professor.getEmail() ,formatCpf(professor.getCpf()),professor.getDegree(),formatDate(professor.getDate()));
    }

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
                        formatCpf(Professor.getCpf()),
                        Professor.getDegree(),
                        formatDate(Professor.getDate())))
                .collect(Collectors.toList());

        return prdList;
    }

    private String formatDate(Date date){
        String dateArray[] = date.toString().split("-");
        return dateArray[2] + "/" + dateArray[1] + "/" + dateArray[0];
    }

    private String formatCpf(String cpf){
        return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }
}

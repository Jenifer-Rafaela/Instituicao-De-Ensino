package com.A3.Trabalho.DTO.Professors;

import com.A3.Trabalho.Model.Professor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProfessorResponseDTOTest {

    private ProfessorResponseDTO professorResponseDTO = new ProfessorResponseDTO();

    @DisplayName("Testa método ProfessorToDTOList")
    @Test
    void ProfessorToDTOList() {
        Professor professor1 = new Professor(2L, "Jenifer", "jenifer@gmail.com", "11052654711", "Mestre", Date.valueOf("1980-05-13"));
        Professor professor2 = new Professor(3L, "João", "joão@gmail.com", "17345656756", "Mestre", Date.valueOf("1982-01-02"));

        List<ProfessorResponseDTO> prList = professorResponseDTO.professorToDTOList(List.of(professor1, professor2));
        String cpf = "110.526.547-11";
        String cpf2 = "173.456.567-56";
        String date = "13-05-1980";
        String date2 = "02-01-1982";

        assertEquals(professor1.getId(), prList.get(0).getId());
        assertEquals(professor1.getName(), prList.get(0).getName());
        assertEquals(professor1.getEmail(), prList.get(0).getEmail());
        assertEquals(cpf, prList.get(0).getCpf());
        assertEquals(professor1.getDegree(), prList.get(0).getDegree());
        assertEquals(date, prList.get(0).getDate());

        assertEquals(professor2.getId(), prList.get(1).getId());
        assertEquals(professor2.getName(), prList.get(1).getName());
        assertEquals(professor2.getEmail(), prList.get(1).getEmail());
        assertEquals(cpf2, prList.get(1).getCpf());
        assertEquals(professor2.getDegree(), prList.get(1).getDegree());
        assertEquals(date2, prList.get(1).getDate());
    }
}

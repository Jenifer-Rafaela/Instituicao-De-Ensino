package com.A3.Trabalho.DTO.Professors;

import com.A3.Trabalho.Model.Professor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProfessorClassResponseDTOTest {

    private ProfessorClassResponseDTO professorClassResponseDTO = new ProfessorClassResponseDTO();

    @DisplayName("Testa método professorToDTOList")
    @Test
    void professorToDTOList() {
        Professor professor1 = new Professor(2L, "Jenifer", "jenifer@gmail.com", "11052654711", "Mestre", Date.valueOf("1980-05-13"));
        Professor professor2 = new Professor(3L, "João", "joão@gmail.com", "17345656756", "Mestre", Date.valueOf("1982-01-02"));

        List<ProfessorClassResponseDTO> pcrList = professorClassResponseDTO.professorToDTOList(List.of(professor1, professor2));

        assertEquals(professor1.getId(), pcrList.get(0).getId());
        assertEquals(professor1.getName(), pcrList.get(0).getName());
        assertEquals(professor1.getEmail(), pcrList.get(0).getEmail());
        assertEquals(professor1.getDegree(), pcrList.get(0).getDegree());

        assertEquals(professor2.getId(), pcrList.get(1).getId());
        assertEquals(professor2.getName(), pcrList.get(1).getName());
        assertEquals(professor2.getEmail(), pcrList.get(1).getEmail());
        assertEquals(professor2.getDegree(), pcrList.get(1).getDegree());
    }
}

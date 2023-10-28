package com.A3.Curso.DTO.Classes;

import com.A3.Curso.Model.Classes;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor

public class ClassesProfessorResponseDTO {

    private long id;
    private String className;
    private String classRoom;
    private String time;
    private String classday;
    private long professorId;
    private String professorName;
    private String professorDegree;


    public ClassesProfessorResponseDTO classesToDTO(Classes classes) {
        ClassesProfessorResponseDTO classesResponseDTO = new ClassesProfessorResponseDTO(classes.getId(),
                classes.getClassName(),
                classes.getClassRoom(),
                classes.getTime(),
                classes.getClassday(),
                classes.getProfessor().getId(),
                classes.getProfessor().getName(),
                classes.getProfessor().getDegree());
        return classesResponseDTO;
    }

    public List<ClassesProfessorResponseDTO> classesToDTOList(List<Classes> classes) {
        List<ClassesProfessorResponseDTO> crdList = new ArrayList<>();

        for (Classes classes1 : classes) {
            crdList.add(classesToDTO(classes1));
        }

        return crdList;
    }

}

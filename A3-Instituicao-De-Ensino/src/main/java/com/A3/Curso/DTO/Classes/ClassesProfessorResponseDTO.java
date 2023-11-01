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
/**
 * DTO para mostrar informações específicas da turma e do seu professor
 * */
public class ClassesProfessorResponseDTO {

    private long id;
    private String className;
    private String classRoom;
    private String time;
    private String classday;
    private long professorId;
    private String professorName;
    private String professorDegree;


    /**
     * <strong>Método para transformar classes em um ClassesProfessorResponseDTO.</strong>
     * @param classes será transformada em ClassesProfessorResponseDTO.
     * @return ClassesProfessorResponseDTO.
     * */
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
}

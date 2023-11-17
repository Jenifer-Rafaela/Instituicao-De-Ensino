package com.A3.Trabalho.DTO.Classes;

import com.A3.Trabalho.Model.Classes;
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
 * DTO para mostrar informações específicas da turma.
 * */
public class ClassesResponseDTO {

    private long id;
    private String className;
    private String classRoom;
    private String time;
    private String classday;

    /**
     * <strong>Método para transformar Classes em ClassesResponseDTO.</strong>
     * @param classes será transformada em ClassesResponseDTO.
     * @return ClassesResponseDTO.
     * */
    public ClassesResponseDTO classesToDTO(Classes classes) {
        ClassesResponseDTO classesResponseDTO = new ClassesResponseDTO(classes.getId(),
                classes.getClassName(),
                classes.getClassRoom(),
                classes.getTime(),
                classes.getClassday());
        return classesResponseDTO;
    }

    /**
     * <strong>Método para transformar uma lista de Classes em uma lista ClassesResponseDTO.</strong>
     * @param classes lista que será transformada em uma lista ClassesResponseDTO.
     * @return ClassesResponseDTO.
     * */
    public List<ClassesResponseDTO> classesToDTOList(List<Classes> classes) {
        List<ClassesResponseDTO> crdList = new ArrayList<>();

        for (Classes classes1 : classes) {
            crdList.add(classesToDTO(classes1));
        }

        return crdList;
    }

}

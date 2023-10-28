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

public class ClassesResponseDTO {

    private long id;
    private String className;
    private String classRoom;
    private String time;
    private String classday;


    public ClassesResponseDTO classesToDTO(Classes classes) {
        ClassesResponseDTO classesResponseDTO = new ClassesResponseDTO(classes.getId(),
                classes.getClassName(),
                classes.getClassRoom(),
                classes.getTime(),
                classes.getClassday());
        return classesResponseDTO;
    }

    public List<ClassesResponseDTO> classesToDTOList(List<Classes> classes) {
        List<ClassesResponseDTO> crdList = new ArrayList<>();

        for (Classes classes1 : classes) {
            crdList.add(classesToDTO(classes1));
        }

        return crdList;
    }

}

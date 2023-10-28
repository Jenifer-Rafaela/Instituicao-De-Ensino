package com.A3.Curso.DTO.Classes;

import com.A3.Curso.Model.Classes;
import com.A3.Curso.Model.Professor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ClassesDTO {

    private String className;
    private String classRoom;
    private String time;
    private String classday;
    private long professor;

    public ClassesDTO(String className, String classRoom, String time, String classday, String professors) {
        this.className = className;
        this.classRoom = classRoom;
        this.time = time;
        this.classday = classday;
        this.professor = Long.parseLong(professors);
    }

    //Transforma DTO em Classe
    public Classes DTOToClass(Professor professor) {
        return new Classes(className, classRoom, time, classday, professor);
    }

    public List<ClassesDTO> classesToDTOList(List<Classes> classes) {
        List<ClassesDTO> crdList = new ArrayList<>();

        for (Classes classes1 : classes) {
            crdList.add(classesToDTO(classes1));
        }

        return crdList;
    }

    public ClassesDTO classesToDTO(Classes classes) {
        return new ClassesDTO(classes.getClassName(), classes.getClassRoom(), classes.getTime(), classes.getClassday(), String.valueOf(classes.getProfessor().getId()));
    }

    public Classes DTOToClass(Classes classes, Professor professor) {
        return new Classes(classes.getId(), className, classRoom, time, classday, professor);
    }

}

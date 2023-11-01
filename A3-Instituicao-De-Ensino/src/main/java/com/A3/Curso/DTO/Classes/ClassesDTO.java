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
/**
 * DTO para receber informações da view (class.html).
 * */
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

    /**
     * <strong>Método para transformar ClassesDTO em Classes.</strong>
     *
     * @param professor será incluído em Classes.
     * @return Classes.
     */
    public Classes DTOToClass(Professor professor) {
        return new Classes(className, classRoom, time, classday, professor);
    }

    /**
     * <strong>Método para transformar uma lista de Classes em uma lista de ClassesDTO.</strong>
     *
     * @param classes lista de classes para serem transformadas.
     * @return ClassesDTO.
     */
    public List<ClassesDTO> classesToDTOList(List<Classes> classes) {
        List<ClassesDTO> crdList = new ArrayList<>();

        for (Classes classes1 : classes) {
            crdList.add(classesToDTO(classes1));
        }

        return crdList;
    }

    /**
     * <strong>Método para transformar classes em ClassesDTO.</strong>
     *
     * @param classes será transformada em ClassesDTO.
     * @return ClassesDTO.
     */
    public ClassesDTO classesToDTO(Classes classes) {
        return new ClassesDTO(classes.getClassName(), classes.getClassRoom(), classes.getTime(), classes.getClassday(), String.valueOf(classes.getProfessor().getId()));
    }

    /**
     * <strong>Método para transformar ClassesDTO em Classes.</strong>
     *
     * @param classes   classe que receberá as informações de ClassesDTO.
     * @param professor professor que será incluído em classes.
     * @return classes.
     */
    public Classes DTOToClassUpdate(Classes classes, Professor professor) {
        classes.setClassName(className);
        classes.setClassRoom(classRoom);
        classes.setTime(time);
        classes.setClassday(classday);
        classes.setProfessor(professor);
        return classes;
    }

}

package com.A3.Curso.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Classes {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    private String className;
    private String classRoom;
    private String time;
    private String classday;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "professor_id", nullable = false)
    private Professor professor;

    @ManyToMany(fetch = FetchType.EAGER,
            mappedBy = "classes")
    private List<Student> students;

    public Classes(String className, String classRoom, String time, String classday, Professor professor) {
        this.className = className;
        this.classRoom = classRoom;
        this.time = time;
        this.classday = classday;
        this.professor = professor;
    }

    public Classes(long id, String className, String classRoom, String time, String classday, Professor professor) {
        this.id = id;
        this.className = className;
        this.classRoom = classRoom;
        this.time = time;
        this.classday = classday;
        this.professor = professor;
    }
}
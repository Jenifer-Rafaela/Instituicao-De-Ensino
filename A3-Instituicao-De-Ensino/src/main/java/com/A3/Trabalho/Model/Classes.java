package com.A3.Trabalho.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class Classes {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    @Column(nullable = false)
    private String className;
    @Column(nullable = false)
    private String classRoom;
    @Column(nullable = false)
    private String time;
    @Column(nullable = false)
    private String classday;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "professor_id", nullable = false)
    private Professor professor;

    @ManyToMany(fetch = FetchType.LAZY,
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
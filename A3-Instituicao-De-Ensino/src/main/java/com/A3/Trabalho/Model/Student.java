package com.A3.Trabalho.Model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.List;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_seq")
    @SequenceGenerator(name = "student_seq", sequenceName = "student_seq", initialValue = 1000)
    private long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, length = 11, unique = true)
    private String cpf;
    @Column(nullable = false)
    private String shift;
    @Column(nullable = false)
    private Date date;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "classes_students",
            joinColumns = @JoinColumn(name = "students_id"),
            inverseJoinColumns = @JoinColumn(name = "classes_id")
    )
    private List<Classes> classes;

    public Student(long id, String name, String email, String cpf, String shift, Date date) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.shift = shift;
        this.date = date;
    }

    public Student(String name, String email, String cpf, String shift, Date date) {
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.shift = shift;
        this.date = date;
    }
}

package com.A3.Trabalho.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, length = 11, unique = true)
    private String cpf;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "professor")
    private List<Classes> classes;

    @Column(nullable = false)
    private String degree;

    @Column
    private Date date;

    public Professor(String name, String email, String cpf, String degree, Date date) {
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.degree = degree;
        this.date = date;
    }

    public Professor(long id, String name, String email, String cpf, String degree, Date date) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.degree = degree;
        this.date = date;
    }
}

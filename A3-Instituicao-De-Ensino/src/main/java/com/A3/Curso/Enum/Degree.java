package com.A3.Curso.Enum;

public enum Degree {
    MESTRE("Mestre"),
    DOUTOR("Doutor");

    String degree;

    Degree(String d) {
        this.degree = d;
    }

    public String getDegree() {
        return this.degree;

    }
}


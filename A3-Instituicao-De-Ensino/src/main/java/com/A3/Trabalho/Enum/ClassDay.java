package com.A3.Trabalho.Enum;

public enum ClassDay {
    Segunda_Terca("Segundas e Terças"),
    Segunda_Quinta("Segundas e Quintas"),
    Segunda_Sexta("Segundas e Sextas"),
    Terca_Quinta("Terças e Quintas"),
    Terca_Sexta("Terças e Sextas"),
    Quinta_Sexta("Quintas e Sextas");


    String classDay;

    ClassDay(String cd) {
        this.classDay = cd;
    }

    public String getClassDay() {
        return this.classDay;
    }
}

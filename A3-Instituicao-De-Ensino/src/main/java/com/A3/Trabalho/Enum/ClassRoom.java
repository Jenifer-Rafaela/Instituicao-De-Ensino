package com.A3.Trabalho.Enum;

public enum ClassRoom {
    INFORMATICA2("Sala: Informática 2"),
    INFORMATICA4("Sala: Informática 4"),
    INFORMATICA5("Sala: Informática 5"),
    SALA1("Sala: 1"),
    SALA2("Sala: 2"),
    SALA3("Sala: 3"),
    SALA4("Sala: 4"),
    SALA5("Sala: 5");

    private String classRoom;

    ClassRoom(String cr) {
        this.classRoom = cr;
    }

    public String getClassRoom() {
        return classRoom;
    }


}

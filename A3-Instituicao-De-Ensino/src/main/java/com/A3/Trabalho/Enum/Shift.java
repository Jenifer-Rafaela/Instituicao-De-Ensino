package com.A3.Trabalho.Enum;

public enum Shift {
    MANHA("Manhã"),
    TARDE("Tarde"),
    NOITE("Noite");

    String shift;

    Shift(String s) {
        this.shift = s;
    }

    public String getShift() {
        return this.shift;
    }
}

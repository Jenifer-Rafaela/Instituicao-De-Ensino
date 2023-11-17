package com.A3.Trabalho.Enum;

public enum Time {
    MANHA("08:00 - 11:40"),
    TARDE("14:00 - 16:40"),
    NOITE("19:00 - 21:40");


    String time;

    Time(String t) {
        this.time = t;
    }

    public String getTime() {
        return this.time;
    }

    public static String getTime(String time) {
        if (time.equals("08:00 - 11:40")) return "Manhã";
        else if (time.equals("14:00 - 16:40")) return "Tarde";
        else return "Noite";
    }
}

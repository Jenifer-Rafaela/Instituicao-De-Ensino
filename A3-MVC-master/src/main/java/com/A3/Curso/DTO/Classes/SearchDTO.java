package com.A3.Curso.DTO.Classes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class SearchDTO {
    @Getter
    private long id;
    @Getter
    @Setter
    private String name;
}

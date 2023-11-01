package com.A3.Curso.DTO.Classes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
/**
 * DTO para receber informações da view e fazer buscas no banco de dados.
 * */
public class SearchDTO {
    private long id;
    @Setter private String name;
}

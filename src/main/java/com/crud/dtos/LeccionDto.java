package com.crud.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LeccionDto {
    Long id;
    String titulo;
    String contenido;
    String videoUrl;
    CursoDto curso;

}

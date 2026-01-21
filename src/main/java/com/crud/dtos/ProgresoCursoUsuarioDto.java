package com.crud.dtos;

import com.crud.security.dtos.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProgresoCursoUsuarioDto  {
    Long id;
    String estado;
    LocalDate fechaInscripcion;
    CursoDto curso;
    UserDto user;




}

package com.crud.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AsesorSimpleDto {
    private Long id;
    private String nombreCompleto;
    private String correo;
    private String telefono;
    private String dni;
}

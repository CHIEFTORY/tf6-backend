package com.crud.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Asesor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreCompleto;

    private String correo;

    private String telefono;

    @Column(unique = true)
    private String dni;

    @ManyToOne
    @JoinColumn(name = "id_curso", nullable = true) // <--- CAMBIO CLAVE AQUÍ
    @JsonBackReference("curso-asesores")// @JsonBackReference // Si la necesitas para serialización/deserialización
    private Curso curso;


}

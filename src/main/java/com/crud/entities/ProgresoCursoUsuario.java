package com.crud.entities;

import com.crud.security.entities.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProgresoCursoUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String estado;



    private LocalDate fechaInscripcion;

    @ManyToOne
    @JoinColumn(name = "id_curso",nullable = true)
    @JsonBackReference("curso-progresos")
    private Curso curso;

    @ManyToOne
    @JoinColumn(name = "id_user",nullable = true)
    @JsonBackReference
    private User user;
}

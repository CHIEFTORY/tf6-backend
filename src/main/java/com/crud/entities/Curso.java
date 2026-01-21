package com.crud.entities;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nombreCurso;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    private String dificultad;

    private LocalDate fechaRegistrada;

    @OneToMany(mappedBy = "curso", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonManagedReference("curso-lecciones") // <--- AÑADE UN NOMBRE ÚNICO AQUÍ
    private List<Leccion> lecciones;

    @OneToMany(mappedBy = "curso", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonManagedReference("curso-asesores") // <--- AÑADE UN NOMBRE ÚNICO AQUÍ
    private List<Asesor> asesores;

    @OneToMany(mappedBy = "curso", cascade = {CascadeType.PERSIST, CascadeType.MERGE}) // Opcional
    @JsonManagedReference("curso-progresos") // <--- AÑADE UN NOMBRE ÚNICO AQUÍ (si existe ProgresoCursoUsuario)
    private List<ProgresoCursoUsuario> progresos;



}

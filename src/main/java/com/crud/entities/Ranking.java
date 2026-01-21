package com.crud.entities;

import com.crud.security.entities.User;
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
public class Ranking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int puntos;
    @OneToOne // Cambiar a OneToOne si un Ranking pertenece a UN ÚNICO User y viceversa
    @JoinColumn(name = "user_id", unique = true)
    @JsonBackReference// user_id será la FK, y debe ser única
    private User user;
}

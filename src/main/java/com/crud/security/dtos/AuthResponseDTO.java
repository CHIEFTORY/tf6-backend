package com.crud.security.dtos;


import java.util.Set;

@lombok.Data
public class AuthResponseDTO {
    private String jwt;
    private Set<String> roles;
    private Long userId; // ¡Añade esta línea!
    private String username;
}
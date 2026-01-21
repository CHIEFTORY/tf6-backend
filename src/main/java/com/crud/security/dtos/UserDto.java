package com.crud.security.dtos;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


public class UserDto {
    Long id;
    String username;
    private String password;

    private Set<String> roles;
}

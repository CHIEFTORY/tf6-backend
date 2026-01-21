// RoleService.java
package com.crud.security.services;

import com.crud.security.entities.Role;
import com.crud.security.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository; // Inyecta el RoleRepository

    // Método para obtener todos los roles
    public List<Role> getAllRoles() {
        return roleRepository.findAll(); // findAll() es un método de JpaRepository
    }

    // Opcional: Si necesitas buscar un rol por nombre
    public Role getRoleByName(String roleName) {
        return roleRepository.findByName(roleName).orElse(null);
    }
}
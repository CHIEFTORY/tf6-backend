// RoleController.java (Nuevo archivo)
package com.crud.security.controllers;

import com.crud.security.entities.Role;
import com.crud.security.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api") // Ruta base para los endpoints de roles
// @CrossOrigin(origins = "${ip.frontend}") // Asegúrate de configurar tu CORS si no lo tienes globalmente
@CrossOrigin(origins = "${ip.frontend}", allowCredentials = "true", exposedHeaders = "Authorization")
public class RoleController {

    @Autowired
    private RoleService roleService; // Inyecta tu nuevo RoleService

    // Endpoint para listar todos los roles
    @GetMapping("/listRoles")
    @PreAuthorize("hasAnyRole('ADMIN')") // Solo los administradores deberían ver la lista de roles
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    // Puedes añadir más endpoints si necesitas crear, actualizar o eliminar roles a través de la API
}
package com.crud.security.controllers;

import com.crud.dtos.LeccionDto;
import com.crud.entities.Leccion;
import com.crud.security.dtos.UserDto;
import com.crud.security.entities.Role;
import com.crud.security.entities.User;
import com.crud.security.repositories.RoleRepository;
import com.crud.security.services.CustomUserDetailsService;
import com.crud.security.services.UserService;
import com.crud.security.util.JwtUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

//@CrossOrigin(origins = "${ip.frontend}")
@CrossOrigin(origins = "${ip.frontend}", allowCredentials = "true", exposedHeaders = "Authorization") //para cloud
@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder bcrypt;
    @Autowired // Inyectar JwtUtil
    private JwtUtil jwtUtil;
    @Autowired // Inyectar CustomUserDetailsService
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private RoleRepository roleRepository; // Inyecta el RoleRepository

    @PostMapping("/user")

    @PreAuthorize("hasRole('ADMIN')") // Solo admins pueden crear usuarios
    public void createUser(@RequestBody User user) {
        String bcryptPassword = bcrypt.encode(user.getPassword());
        user.setPassword(bcryptPassword);
        // Assign a default role if none are provided (e.g., 'USER')
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            Set<Role> defaultRoles = new HashSet<>();
            roleRepository.findByName("ROLE_USER")
                    .ifPresent(defaultRoles::add);
            user.setRoles(defaultRoles);
        }
        userService.save(user);
    }

    @PostMapping("/save/{user_id}/{rol_id}")

    public ResponseEntity<Integer> saveUseRol(@PathVariable("user_id") Long user_id,
                                              @PathVariable("rol_id") Long rol_id) {
        Integer result = userService.insertUserRol(user_id, rol_id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/listarUser")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> listUser() { // Change return type
        List<User> users = userService.listUser(); // Fetch entities

        // You might want to inject ModelMapper or make it a singleton
        ModelMapper modelMapper = new ModelMapper();

        List<UserDto> userDTOs = users.stream()
                .map(user -> {
                    UserDto userDto = modelMapper.map(user, UserDto.class);
                    // Manually map roles from Set<Role> to Set<String> (Role names)
                    userDto.setRoles(user.getRoles().stream()
                            .map(Role::getName)
                            .collect(Collectors.toSet()));
                    return userDto;
                })
                .collect(Collectors.toList());

        return new ResponseEntity<>(userDTOs, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long id) {
        User currentUser = userService.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));

        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto = modelMapper.map(currentUser, UserDto.class);

        userDto.setRoles(currentUser.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet()));

        return ResponseEntity.ok(userDto);
    }
    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        try {
            userService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content si es exitoso
        } catch (IllegalArgumentException e) {
            // Manejar la excepción específica de que no se puede eliminar un ADMIN
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); // 403 Forbidden
            // También podrías devolver un mensaje en el cuerpo:
            // return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error: " + e.getMessage());
        } catch (RuntimeException e) {
            // Manejar otras excepciones, como usuario no encontrado
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
            // O: return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
        }
    }


    // NEW: Endpoint to update a user (username and/or password)
    @PutMapping("/users/update") // Usar @PutMapping para operaciones de actualización
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')") // Ajusta los roles según sea necesario
    public ResponseEntity<UserDto> updateUser(@RequestBody User userUpdates) {
        // 1. Obtener el usuario existente de la base de datos
        User existingUser = userService.findById(userUpdates.getId()) // Usar userUpdates.getId()
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado para actualizar con ID: " + userUpdates.getId()));

        boolean usernameChanged = false;

        // 2. Actualizar el nombre de usuario si es diferente
        // Solo actualiza si el username proporcionado no es nulo y es diferente
        if (userUpdates.getUsername() != null && !userUpdates.getUsername().isEmpty() &&
                !existingUser.getUsername().equals(userUpdates.getUsername())) {
            existingUser.setUsername(userUpdates.getUsername());
            usernameChanged = true;
        }

        // 3. Actualizar la contraseña SOLO si se proporciona una nueva y no está vacía
        if (userUpdates.getPassword() != null && !userUpdates.getPassword().isEmpty()) {
            existingUser.setPassword(bcrypt.encode(userUpdates.getPassword()));
        }

        // 4. Guardar el usuario actualizado
        userService.save(existingUser); // Asume que tu userService.save() guarda los cambios en la DB

        // 5. Preparar la respuesta DTO
        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto = modelMapper.map(existingUser, UserDto.class);

        userDto.setRoles(existingUser.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet()));

        // 6. Generar un nuevo JWT SIEMPRE después de una actualización exitosa,
        // o al menos si el username cambió. Esto simplifica la lógica y garantiza un token fresco.
        HttpHeaders responseHeaders = new HttpHeaders();
        // Cargar UserDetails del usuario actualizado
        UserDetails updatedUserDetails = userDetailsService.loadUserByUsername(existingUser.getUsername());
        final String newToken = jwtUtil.generateToken(updatedUserDetails);
        // *** CRÍTICO: Añadir el prefijo "Bearer " al token ***
        responseHeaders.set("Authorization", "Bearer " + newToken);
        System.out.println("Se ha generado y enviado un nuevo token en la actualización de perfil.");


        return ResponseEntity.ok().headers(responseHeaders).body(userDto);

    }



    // NEW: Endpoint for ADMIN to remove a specific role from a user



}

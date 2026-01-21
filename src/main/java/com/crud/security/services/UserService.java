package com.crud.security.services;


import com.crud.entities.ProgresoCursoUsuario;
import com.crud.repositorios.ProgresoCursoUsuarioRepositorio;
import com.crud.security.entities.User;
import com.crud.security.interfaces.IUserSerivce;
import com.crud.security.repositories.UserRepository;
import org.aspectj.weaver.IUnwovenClassFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserSerivce {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProgresoCursoUsuarioRepositorio progresoCursoUsuarioRepositorio;

    @Transactional
    public void save(User user) {
        System.out.println("DEBUG: UserService.save() - Recibiendo usuario para guardar/actualizar:");
        System.out.println("DEBUG: UserService.save() - ID: " + user.getId() + ", Username (antes de guardar): '" + user.getUsername() + "'");
        System.out.println("DEBUG: UserService.save() - Password (codificada): " + user.getPassword()); // No mostrar en producción por seguridad

        try {
            userRepository.save(user);
            System.out.println("DEBUG: UserService.save() - ¡Usuario guardado exitosamente!");
        } catch (Exception e) {
            System.err.println("ERROR: UserService.save() - Falló al guardar usuario: " + e.getMessage());
            e.printStackTrace(); // Imprime el stack trace completo para ver la causa
            throw e; // Relanza la excepción para que Spring la capture y haga rollback si es necesario
        }
    }

    public Integer insertUserRol(Long user_id, Long rol_id) {
        Integer result = 0;
        userRepository.insertUserRol(user_id, rol_id);
        return 1;
    }

    @Override
    public List<User> listUser() {return userRepository.findAll();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsernameIgnoreCase(username);

    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    public void delete(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User userToDelete = userOptional.get();

            // Verificar si el usuario a eliminar tiene el rol 'ADMIN'
            boolean isAdmin = userToDelete.getRoles().stream()
                    .anyMatch(role -> role.getName().equals("ROLE_ADMIN")); // Asume que el nombre del rol es "ROLE_ADMIN"

            if (isAdmin) {
                // Aquí puedes lanzar una excepción personalizada o una estándar
                throw new IllegalArgumentException("No se puede eliminar un usuario con rol de ADMINISTRADOR.");
                // O: throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No se puede eliminar un usuario administrador.");
            }

            userRepository.delete(userToDelete);
        } else {
            throw new RuntimeException("Usuario no encontrado con ID: " + id);
        }

    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
}

package com.crud.services;

import com.crud.dtos.LeccionDto;
import com.crud.entities.Curso;
import com.crud.entities.Leccion;
import com.crud.interfaces.ILeccionService;
import com.crud.repositorios.CursoRepository;
import com.crud.repositorios.LeccionRepository;
import com.crud.repositorios.ProgresoCursoUsuarioRepositorio;
import com.crud.security.entities.User;
import com.crud.security.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class LeccionService implements ILeccionService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private LeccionRepository leccionRepository;
    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private ProgresoCursoUsuarioRepositorio progresoCursoUsuarioRepositorio;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Leccion grabarLeccion(Leccion leccion) {
        return leccionRepository.save(leccion);
    }

    @Override
    public List<Leccion> listLecciones() {

        return leccionRepository.findAll();
    }

    @Override
    public LeccionDto editar(Leccion leccionDesdeControlador) { // <-- ¡CORREGIDO AQUÍ! Nombre del parámetro para claridad
        // 1. Buscar la lección existente en la base de datos por su ID
        Optional<Leccion> optionalLeccionExistente = leccionRepository.findById(leccionDesdeControlador.getId());

        if (optionalLeccionExistente.isPresent()) {
            Leccion leccionExistente = optionalLeccionExistente.get();

            // 2. Actualizar las propiedades de la lección existente con los valores del objeto recibido
            leccionExistente.setTitulo(leccionDesdeControlador.getTitulo());
            leccionExistente.setContenido(leccionDesdeControlador.getContenido());
            leccionExistente.setVideoUrl(leccionDesdeControlador.getVideoUrl());

            // 3. ¡IMPORTANTE! Manejar la relación con Curso
            if (leccionDesdeControlador.getCurso() != null && leccionDesdeControlador.getCurso().getId() != null) {
                Long cursoId = leccionDesdeControlador.getCurso().getId(); // Get the ID of the Course from the incoming Leccion

                // Fetch the managed Curso entity from the database
                Optional<Curso> optionalCurso = cursoRepository.findById(cursoId);
                if (optionalCurso.isPresent()) {
                    leccionExistente.setCurso(optionalCurso.get()); // Set the managed Curso entity
                } else {
                    // Handle case where Curso with given ID is not found (e.g., throw an exception)
                    throw new RuntimeException("Curso con ID " + cursoId + " no encontrado.");
                }
            } else {
                // If no Curso information is provided, you might choose to
                // 1. Keep the existing course (if it's an update and not explicitly removing)
                // 2. Set the course to null (if that's a valid state for your business logic)
                // For this example, we'll assume a course must always be present or updated.
                // If it's valid to not update the course, remove this 'else' block.
                leccionExistente.setCurso(null); // Or keep existing: leccionExistente.getCurso();
            }

            // 4. Guardar (actualizar) la lección existente y gestionada por JPA
            Leccion leccionGuardada = leccionRepository.save(leccionExistente);

            // 5. Convertir la entidad guardada de nuevo a DTO para retornar
            return modelMapper.map(leccionGuardada, LeccionDto.class);
        }
        // Si no se encuentra la lección, lanza una excepción (por ejemplo, una NotFoundException)
        throw new RuntimeException("Leccion con ID " + leccionDesdeControlador.getId() + " no encontrada para edición.");
    }

    @Override
    public List<Leccion> getLeccionesByCursoId(Long idCurso) {
        return List.of();
    }

    @Override
    public Leccion buscarPorId(Long id) {
        if (leccionRepository.findById(id).isPresent()) {
            return leccionRepository.findById(id).get();
        }
        return null;
    }

    @Override
    public void deleteLeccion(Long id) {
        if (leccionRepository.existsById(id)) {
            leccionRepository.deleteById(id);
        }

    }


    public List<Leccion> getLeccionesByCursoIdForUser(Long cursoId, Long userId) {
        // 1. Verificar si el usuario está inscrito en el curso
        boolean isUserEnrolled = progresoCursoUsuarioRepositorio.existsByCursoIdAndUserId(cursoId, userId);

        if (isUserEnrolled) {
            // Si el usuario está inscrito, retorna todas las lecciones de ese curso
            return leccionRepository.findByCursoId(cursoId);
        } else {
            // Si el usuario no está inscrito, retorna una lista vacía o lanza una excepción
            return List.of(); // O podrías lanzar una AccessDeniedException o CourseNotEnrolledException
        }
    }

    @Override
    public Long getUserIdByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        return userOptional.map(User::getId).orElse(null);
    }
}

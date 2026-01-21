package com.crud.controllers;

import com.crud.dtos.CursoDto;
import com.crud.dtos.LeccionDto;
import com.crud.entities.Curso;
import com.crud.entities.Leccion;
import com.crud.repositorios.LeccionRepository;
import com.crud.services.LeccionService;
import org.springframework.security.core.Authentication;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "${ip.frontend}", allowCredentials = "true", exposedHeaders = "Authorization")
@RequestMapping("/api")
public class LeccionController {
    @Autowired
    private LeccionService leccionService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/crearLeccion")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Leccion> crearLeccion(@RequestBody Leccion leccion) {
        Leccion leccion1 = leccionService.grabarLeccion(leccion);
        return new ResponseEntity<>(leccion1, HttpStatus.OK);

    }

    @GetMapping("/listarLeccion")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public List<LeccionDto> listarLecciones() {
        List<Leccion> leccions =leccionService.listLecciones();
        ModelMapper mapper = new ModelMapper();
        return leccions.stream()
                .map(l -> mapper.map(l, LeccionDto.class))
                .collect(Collectors.toList());
    }

    @PutMapping("/editarLeccion")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LeccionDto> editarLeccion(@RequestBody LeccionDto leccionDto) {
        // 1. Mapeamos el DTO de entrada a la entidad Leccion para pasar al servicio
        Leccion leccionAActualizar = modelMapper.map(leccionDto, Leccion.class);

        // 2. Llamamos al servicio, que ahora devuelve directamente el DTO actualizado
        LeccionDto leccionDtoActualizada = leccionService.editar(leccionAActualizar); // <-- ¡CORREGIDO AQUÍ!

        // 3. Devolvemos la respuesta con el DTO actualizado recibido del servicio
        // Ya no es necesario un segundo mapeo aquí
        return ResponseEntity.ok(leccionDtoActualizada);
    }
    @DeleteMapping("/leccion/{id}")
    public void eliminarLeccion(@PathVariable long id) {
        leccionService.deleteLeccion(id);
    }

    @GetMapping("/leccion/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LeccionDto> buscaLeccion (@PathVariable long id) {
        ModelMapper modelMapper = new ModelMapper();
        Leccion leccion = leccionService.buscarPorId(id);
        LeccionDto leccionDto = modelMapper.map(leccion, LeccionDto.class);
        return ResponseEntity.ok(leccionDto);
    }


    @GetMapping("/lecciones/curso/{cursoId}/my-lessons") // Nueva URL para indicar que es para el usuario actual
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<LeccionDto>> getLeccionesByCursoIdForAuthenticatedUser(
            @PathVariable Long cursoId) {

        // Obtener el nombre de usuario (username) del contexto de seguridad
        // Asumiendo que tu "principal" en Spring Security es el username.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Esto obtendrá el username

        // Ahora necesitas obtener el ID del usuario a partir de su username.
        // Esto implica inyectar tu UserRepository o un UserService que pueda hacer esto.
        // Asumo que tienes un UserRepository o un servicio que puede mapear username a userId.
        Long userId = leccionService.getUserIdByUsername(username); // Necesitarás crear este método en LeccionService

        if (userId == null) {
            // Esto no debería ocurrir si el usuario está autenticado, pero es una buena práctica manejarlo.
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        List<Leccion> lecciones = leccionService.getLeccionesByCursoIdForUser(cursoId, userId);

        if (lecciones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<LeccionDto> leccionDtos = lecciones.stream()
                .map(leccion -> modelMapper.map(leccion, LeccionDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(leccionDtos);
    }


}

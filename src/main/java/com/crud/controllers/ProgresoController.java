package com.crud.controllers;

import com.crud.dtos.CursoDto;
import com.crud.dtos.LeccionDto;
import com.crud.dtos.ProgresoCursoUsuarioDto;
import com.crud.entities.Curso;
import com.crud.entities.Leccion;
import com.crud.entities.ProgresoCursoUsuario;
import com.crud.exception.ResourceNotFoundException;
import com.crud.services.ProgresoService;
import org.springframework.security.core.Authentication;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "${ip.frontend}", allowCredentials = "true", exposedHeaders = "Authorization")
@RequestMapping("/api")
public class ProgresoController {


    @Autowired
    private ProgresoService progresoService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/listarProgreso")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")

    public ResponseEntity<List<ProgresoCursoUsuarioDto>> listarProgresos() {
        List<ProgresoCursoUsuario> progresos = progresoService.listProgreso();
        List<ProgresoCursoUsuarioDto> progresoDtos = progresos.stream()
                .map(p -> modelMapper.map(p, ProgresoCursoUsuarioDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(progresoDtos);
    }


    @PostMapping("/registrarProgreso")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> grabarProgreso(@RequestBody ProgresoCursoUsuarioDto progresoCursoUsuarioDto) {
        try {
            ProgresoCursoUsuario progresoEntity = modelMapper.map(progresoCursoUsuarioDto, ProgresoCursoUsuario.class);
            ProgresoCursoUsuario savedProgreso = progresoService.grabarProgresoCursoUsuario(progresoEntity);
            return new ResponseEntity<>(modelMapper.map(savedProgreso, ProgresoCursoUsuarioDto.class), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) { // Captura la excepción que ProgresoService.grabarProgresoCursoUsuario lanza
            // Podrías lanzar tu propia DuplicateEnrollmentException desde el servicio
            // y capturarla aquí si lo prefieres para más semántica.
            return new ResponseEntity<>(null, HttpStatus.CONFLICT); // 409 Conflict para duplicados
        } catch (Exception e) {
            System.err.println("Error al grabar progreso: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
        }
    }

    @GetMapping("/filtrarProgreso/filterByUsername")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public List<ProgresoCursoUsuarioDto> listarProgresoByUsername(@RequestParam String username){
        List<ProgresoCursoUsuario> progresos = progresoService.findProgressByUsername(username);
        ModelMapper mapper = new ModelMapper();
        return progresos.stream()
                .map(c -> mapper.map(c, ProgresoCursoUsuarioDto.class))
                .collect(Collectors.toList());
    }

    @PutMapping("/editarProgreso")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<ProgresoCursoUsuarioDto>  editarProgreso(@RequestBody ProgresoCursoUsuarioDto progresoCursoUsuarioDto) {
        try {
            ProgresoCursoUsuario progresoToUpdate = modelMapper.map(progresoCursoUsuarioDto, ProgresoCursoUsuario.class);
            ProgresoCursoUsuario updatedProgreso = progresoService.editar(progresoToUpdate);
            // El servicio 'editar' ahora maneja la actualización del ranking internamente
            return ResponseEntity.ok(modelMapper.map(updatedProgreso, ProgresoCursoUsuarioDto.class));
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
        } catch (Exception e) {
            System.err.println("Error al editar progreso: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
        }
    }
    @DeleteMapping("/progreso/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarProgreso(@PathVariable long id) {
        try {
            progresoService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content
        } catch (ResourceNotFoundException e) { // Asumiendo que el delete también podría lanzar esta excepción si el ID no existe
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Error al eliminar progreso: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/progreso/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ProgresoCursoUsuarioDto> buscarProgresoPorId(@PathVariable long id) {
        try {
            ProgresoCursoUsuario progreso = progresoService.buscarPorId(id);
            return ResponseEntity.ok(modelMapper.map(progreso, ProgresoCursoUsuarioDto.class));
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
        } catch (Exception e) {
            System.err.println("Error al buscar progreso por ID: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
        }
    }

    @PutMapping("/progreso/{id}/completar")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ProgresoCursoUsuarioDto> marcarCursoCompletado(@PathVariable Long id) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String usernameActual = authentication.getName();

            // 1. Buscar el progreso existente por ID
            ProgresoCursoUsuario progresoExistente = progresoService.buscarPorId(id);
            if (progresoExistente == null) {
                throw new ResourceNotFoundException("Progreso de curso no encontrado con ID: " + id);
            }

            System.out.println("DEBUG en Controller: Progreso ID " + id + ", Estado al inicio: " + progresoExistente.getEstado());

            // 2. Verificar que el usuario logueado sea el propietario del progreso
            if (progresoExistente.getUser() == null || !progresoExistente.getUser().getUsername().equals(usernameActual)) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN); // 403 Forbidden
            }

            // 3. Verificar que el curso no esté ya completado para evitar actualizaciones innecesarias
            if ("COMPLETADO".equalsIgnoreCase(progresoExistente.getEstado())) {
                System.out.println("DEBUG en Controller: Curso ya está COMPLETADO, regresando sin actualizar.");
                return ResponseEntity.ok(modelMapper.map(progresoExistente, ProgresoCursoUsuarioDto.class));
            }

            // 4. ELIMINAR ESTA LÍNEA: progresoExistente.setEstado("COMPLETADO");

            // 5. LLAMAR A UN NUEVO MÉTODO EN EL SERVICIO PARA MARCAR COMO COMPLETADO
            // Este nuevo método se encargará de:
            //   a) Re-obtener el progreso (si es necesario)
            //   b) Cambiar su estado a "COMPLETADO"
            //   c) Aplicar la lógica del ranking
            ProgresoCursoUsuario updatedProgreso = progresoService.marcarComoCompletado(id); // <--- NUEVA LLAMADA AL SERVICIO

            return ResponseEntity.ok(modelMapper.map(updatedProgreso, ProgresoCursoUsuarioDto.class));
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
        } catch (Exception e) {
            System.err.println("Error al marcar curso como completado: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
        }
    }

}

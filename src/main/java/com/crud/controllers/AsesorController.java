package com.crud.controllers;

import com.crud.dtos.AsesorDto;
import com.crud.dtos.LeccionDto;
import com.crud.dtos.ProgresoCursoUsuarioDto;
import com.crud.entities.Asesor;
import com.crud.entities.Leccion;
import com.crud.entities.ProgresoCursoUsuario;
import com.crud.services.AsesorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "${ip.frontend}", allowCredentials = "true", exposedHeaders = "Authorization")
@RequestMapping("/api")
public class AsesorController {

    @Autowired
    private AsesorService asesorService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/crearAsesor")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Asesor> crearAsesor(@RequestBody Asesor asesor) {
        Asesor asesor1 = asesorService.grabarAsesor(asesor);
        return new ResponseEntity<>(asesor1, HttpStatus.OK);

    }

    @GetMapping("/listarAsesor")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public List<AsesorDto> listarAsesor() {
        List<Asesor> asesors =asesorService.listarAsesores();
        ModelMapper mapper = new ModelMapper();
        return asesors.stream()
                .map(l -> mapper.map(l, AsesorDto.class))
                .collect(Collectors.toList());
    }

    @PutMapping("/editarAsesor")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AsesorDto> editarLAsesor(@RequestBody AsesorDto asesorDto) {
        // 1. Mapeamos el DTO de entrada a la entidad Leccion para pasar al servicio
        Asesor asesorActualizar = modelMapper.map(asesorDto, Asesor.class);

        // 2. Llamamos al servicio, que now returns AsesorDto
        AsesorDto asesorDtoActualizada = asesorService.editar(asesorActualizar); // This line is now correct

        // 3. Devolvemos la respuesta con el DTO actualizado recibido del servicio
        return ResponseEntity.ok(asesorDtoActualizada);
    }


    @DeleteMapping("/asesor/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void eliminarAsesor(@PathVariable long id) {
        asesorService.delete(id);
    }

    @GetMapping("/asesor/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<AsesorDto> buscaAsesor (@PathVariable long id) {
        ModelMapper modelMapper = new ModelMapper();
        Asesor asesor = asesorService.buscarPorId(id);
        AsesorDto asesorDto = modelMapper.map(asesor, AsesorDto.class);
        return ResponseEntity.ok(asesorDto);
    }
}

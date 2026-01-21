package com.crud.controllers;

import com.crud.dtos.CursoDto;
import com.crud.entities.Curso;
import com.crud.services.CursoService;
import jakarta.persistence.Access;
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
public class CursoController {
    @Autowired
    private CursoService cursoService;

    @GetMapping("/listarCurso")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")

    public List<CursoDto> listarCurso(){
        List<Curso> cursos =cursoService.listarCursos();
        ModelMapper mapper = new ModelMapper();
        return cursos.stream()
                .map(c -> mapper.map(c, CursoDto.class))
                .collect(Collectors.toList());
    }


    @PostMapping("/registrarCurso")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CursoDto> registrarCurso(@RequestBody CursoDto cursoDto){
        ModelMapper modelMapper = new ModelMapper();
        Curso curso = modelMapper.map(cursoDto, Curso.class);
        curso = cursoService.grabarCurso(curso);
        cursoDto = modelMapper.map(curso, CursoDto.class);
        return ResponseEntity.ok(cursoDto);
    }

    @PutMapping("/editarCurso")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CursoDto>  editarCliente(@RequestBody CursoDto cursoDto) {
        ModelMapper modelMapper = new ModelMapper();
        Curso curso = modelMapper.map(cursoDto, Curso.class);
        curso = cursoService.editar(curso);
        cursoDto = modelMapper.map(curso, CursoDto.class);
        return ResponseEntity.ok(cursoDto);
    }


    @DeleteMapping("/curso/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void eliminarCurso(@PathVariable long id) {
        cursoService.delete(id);
    }


    @GetMapping("/curso/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<CursoDto> buscaCurso (@PathVariable long id) {
        ModelMapper modelMapper = new ModelMapper();
        Curso curso = cursoService.buscarPorId(id);
        CursoDto cursoDto = modelMapper.map(curso, CursoDto.class);
        return ResponseEntity.ok(cursoDto);
    }


    // --- NUEVO ENDPOINT PARA OBTENER CURSO CON ASESORES ---
    @GetMapping("/curso/{id}/conAsesores")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')") // Ajusta los roles según necesites
    public ResponseEntity<CursoDto> getCursoConAsesores(@PathVariable Long id) {
        CursoDto cursoDto = cursoService.buscarCursoConAsesoresPorId(id);
        if (cursoDto != null) {
            return ResponseEntity.ok(cursoDto);
        }
        return ResponseEntity.notFound().build();
    }


}

package com.crud.interfaces;

import com.crud.dtos.CursoDto;
import com.crud.dtos.LeccionDto;
import com.crud.entities.Curso;

import java.util.List;

public interface ICursoService {
    public Curso grabarCurso(Curso curso);

    public List<Curso> listarCursos();

    public Curso editar(Curso curso);

    public void delete(Long id);

    Curso buscarPorId(Long id);
    public List<LeccionDto> obtenerLeccionesPorCursoId(Long cursoId);

    public CursoDto buscarCursoConAsesoresPorId(Long id); // Retorna DTO para la API
    // --------------------
}

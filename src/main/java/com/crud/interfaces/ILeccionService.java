package com.crud.interfaces;

import com.crud.dtos.LeccionDto;
import com.crud.entities.Curso;
import com.crud.entities.Leccion;

import java.util.List;

public interface ILeccionService {
    public Leccion grabarLeccion(Leccion leccion);

    public List<Leccion> listLecciones();

    public LeccionDto editar(Leccion leccion);


    public List<Leccion> getLeccionesByCursoId(Long idCurso);

    Leccion buscarPorId(Long id);

    public void deleteLeccion(Long id);

    public List<Leccion> getLeccionesByCursoIdForUser(Long cursoId, Long userId);

    // Nuevo método para obtener el ID de usuario por nombre de usuario
    Long getUserIdByUsername(String username);
}

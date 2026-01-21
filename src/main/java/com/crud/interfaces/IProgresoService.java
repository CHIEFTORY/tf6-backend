package com.crud.interfaces;

import com.crud.dtos.ProgresoCursoUsuarioDto;
import com.crud.entities.Leccion;
import com.crud.entities.ProgresoCursoUsuario;

import java.util.List;

public interface IProgresoService {
    public ProgresoCursoUsuario grabarProgresoCursoUsuario(ProgresoCursoUsuario progresoCursoUsuario);

    public List<ProgresoCursoUsuario> listProgreso();

    public ProgresoCursoUsuario editar(ProgresoCursoUsuario progresoCursoUsuario);

    public void delete(Long id);

    ProgresoCursoUsuario buscarPorId(Long id);

    public List<ProgresoCursoUsuario> findProgressByUsername(String username);

    ProgresoCursoUsuario marcarComoCompletado(Long progresoId);
}

package com.crud.repositorios;

import com.crud.entities.Leccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeccionRepository extends JpaRepository<Leccion, Long> {
    List<Leccion> findByCursoId(Long idCurso);



}

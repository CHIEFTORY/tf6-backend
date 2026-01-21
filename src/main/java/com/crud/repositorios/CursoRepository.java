package com.crud.repositorios;

import com.crud.entities.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CursoRepository extends JpaRepository<Curso, Long> {
    //@Query("SELECT c FROM Curso c JOIN FETCH c.lecciones WHERE c.id = :id")
    //Optional<Curso> findByIdWithLecciones(@Param("id") Long id);

    @Query("SELECT c FROM Curso c LEFT JOIN FETCH c.asesores WHERE c.id = :id")
    Optional<Curso> findByIdWithAsesores(@Param("id") Long id);
}

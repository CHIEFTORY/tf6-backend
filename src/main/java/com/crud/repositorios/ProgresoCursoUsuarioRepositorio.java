package com.crud.repositorios;

import com.crud.entities.Curso;
import com.crud.entities.ProgresoCursoUsuario;
import com.crud.security.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProgresoCursoUsuarioRepositorio extends JpaRepository<ProgresoCursoUsuario, Long>{
    List<ProgresoCursoUsuario> findByUserUsername(String username);
    List<ProgresoCursoUsuario> findByUser(User user);
    void deleteByUser(User user);



    boolean existsByUserAndCurso(User user, Curso curso);


    Optional<ProgresoCursoUsuario> findByCursoAndUser(Curso curso, User user);
    boolean existsByCursoIdAndUserId(Long cursoId, Long userId);
}

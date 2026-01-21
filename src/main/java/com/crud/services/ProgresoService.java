package com.crud.services;


import com.crud.entities.Curso;
import com.crud.entities.ProgresoCursoUsuario;
import com.crud.entities.Ranking;
import com.crud.exception.ResourceNotFoundException;
import com.crud.interfaces.IProgresoService;
import com.crud.repositorios.ProgresoCursoUsuarioRepositorio;
import com.crud.repositorios.RankingRepositorio;
import com.crud.security.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProgresoService implements IProgresoService {

    @Autowired
    private ProgresoCursoUsuarioRepositorio progresoCursoUsuarioRepositorio;
    @Autowired
    private RankingRepositorio rankingRepositorio;

    @Override
    public ProgresoCursoUsuario grabarProgresoCursoUsuario(ProgresoCursoUsuario progresoCursoUsuario) {
        User user = progresoCursoUsuario.getUser();
        Curso curso = progresoCursoUsuario.getCurso();

        if (progresoCursoUsuarioRepositorio.existsByUserAndCurso(user, curso)) {
            throw new IllegalArgumentException("El usuario ya está matriculado en este curso.");
        }
        return progresoCursoUsuarioRepositorio.save(progresoCursoUsuario);
    }

    @Override
    public List<ProgresoCursoUsuario> listProgreso() {
        return progresoCursoUsuarioRepositorio.findAll();
    }

    @Override
    public ProgresoCursoUsuario editar(ProgresoCursoUsuario progresoCursoUsuario) {
        // Este método 'editar' puede seguir existiendo para ediciones generales,
        // pero la lógica de 'marcarComoCompletado' será independiente.
        // Aquí no hay lógica de ranking.
        if (progresoCursoUsuario != null && progresoCursoUsuario.getId() != null) {
            Optional<ProgresoCursoUsuario> existingProgresoOpt = progresoCursoUsuarioRepositorio.findById(progresoCursoUsuario.getId());

            if (existingProgresoOpt.isPresent()) {
                ProgresoCursoUsuario existingProgreso = existingProgresoOpt.get();
                // Actualizar las propiedades que se pueden editar
                existingProgreso.setEstado(progresoCursoUsuario.getEstado());
                existingProgreso.setFechaInscripcion(progresoCursoUsuario.getFechaInscripcion());
                existingProgreso.setCurso(progresoCursoUsuario.getCurso());
                existingProgreso.setUser(progresoCursoUsuario.getUser());
                return progresoCursoUsuarioRepositorio.save(existingProgreso);
            }
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        if (progresoCursoUsuarioRepositorio.existsById(id)){
            progresoCursoUsuarioRepositorio.deleteById(id);
        }

    }

    @Override
    public ProgresoCursoUsuario buscarPorId(Long id) {
        if (progresoCursoUsuarioRepositorio.findById(id).isPresent()) {
            return progresoCursoUsuarioRepositorio.findById(id).get();
        }
        return null;
    }

    @Override
    public List<ProgresoCursoUsuario> findProgressByUsername(String username) {
        return progresoCursoUsuarioRepositorio.findByUserUsername(username);
    }

    @Override
    @Transactional
    public ProgresoCursoUsuario marcarComoCompletado(Long progresoId) {
        // 1. Obtener el progreso (asegurando que es una entidad gestionada por la transacción actual)
        // Usamos findById que puede lanzar una excepción si no se encuentra
        ProgresoCursoUsuario progreso = progresoCursoUsuarioRepositorio.findById(progresoId)
                .orElseThrow(() -> new ResourceNotFoundException("Progreso de curso no encontrado con ID: " + progresoId));

        String estadoAnterior = progreso.getEstado(); // Guarda el estado ANTERIOR

        System.out.println("DEBUG en Service (marcarComoCompletado): Progreso ID " + progresoId + ", Estado ANTES de actualizar: " + estadoAnterior);


        // 2. Marcar el curso como COMPLETADO
        progreso.setEstado("COMPLETADO");
        ProgresoCursoUsuario updatedProgreso = progresoCursoUsuarioRepositorio.save(progreso); // Persistir el cambio

        System.out.println("DEBUG en Service (marcarComoCompletado): Estado DESPUÉS de guardar: " + updatedProgreso.getEstado());

        // 3. Lógica para actualizar el ranking, SOLO si el curso NO ESTABA COMPLETADO antes
        if (!"COMPLETADO".equalsIgnoreCase(estadoAnterior) && "COMPLETADO".equalsIgnoreCase(updatedProgreso.getEstado())) {
            System.out.println("DEBUG en Service (marcarComoCompletado): Condición de ranking CUMPLIDA. Actualizando puntos...");

            User user = updatedProgreso.getUser();
            if (user != null) {
                Optional<Ranking> userRankingOpt = rankingRepositorio.findByUser(user);
                Ranking userRanking;
                if (userRankingOpt.isPresent()) {
                    userRanking = userRankingOpt.get();
                } else {
                    userRanking = new Ranking();
                    userRanking.setUser(user);
                    userRanking.setPuntos(0);
                }
                userRanking.setPuntos(userRanking.getPuntos() + 100); // Sumar 100 puntos
                rankingRepositorio.save(userRanking);
                System.out.println("DEBUG en Service (marcarComoCompletado): Ranking del usuario " + user.getUsername() + " actualizado a " + userRanking.getPuntos() + " puntos.");
            }
        } else {
            System.out.println("DEBUG en Service (marcarComoCompletado): Condición de ranking NO CUMPLIDA. Estado anterior: " + estadoAnterior + ", Estado actual: " + updatedProgreso.getEstado());
        }

        return updatedProgreso;
    }
}

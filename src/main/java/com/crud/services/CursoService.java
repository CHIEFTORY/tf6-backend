package com.crud.services;


import com.crud.dtos.CursoDto;
import com.crud.dtos.LeccionDto;
import com.crud.entities.Asesor;
import com.crud.entities.Curso;
import com.crud.entities.Leccion;
import com.crud.entities.ProgresoCursoUsuario;
import com.crud.interfaces.ICursoService;
import com.crud.repositorios.AsesorRepository;
import com.crud.repositorios.CursoRepository;
import com.crud.repositorios.LeccionRepository;
import com.crud.repositorios.ProgresoCursoUsuarioRepositorio;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class CursoService implements ICursoService {
    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private LeccionRepository leccionRepository; // Necesitarás estos repositorios
    @Autowired
    private AsesorRepository asesorRepository;
    @Autowired
    private ProgresoCursoUsuarioRepositorio progresoCursoUsuarioRepository;


    @Autowired // Inyectar ModelMapper
    private ModelMapper modelMapper;


    @Override
    public Curso grabarCurso(Curso curso) {
        return cursoRepository.save(curso);
    }


    @Override
    public List<Curso> listarCursos() {
        return cursoRepository.findAll();
    }

    @Override
    public Curso editar(Curso cursoActualizado) { // Cambié el nombre del parámetro para mayor claridad
        // 1. Buscar el curso existente en la base de datos por su ID
        Optional<Curso> optionalCursoExistente = cursoRepository.findById(cursoActualizado.getId());

        if (optionalCursoExistente.isPresent()) {
            Curso cursoExistente = optionalCursoExistente.get();


            cursoExistente.setNombreCurso(cursoActualizado.getNombreCurso());
            cursoExistente.setDescripcion(cursoActualizado.getDescripcion());
            cursoExistente.setDificultad(cursoActualizado.getDificultad()); // <--- ¡Esta es la línea clave!

            // 3. Guardar (actualizar) el curso existente y gestionado por JPA
            return cursoRepository.save(cursoExistente);
        }
        // Si no se encuentra el curso, retorna null o lanza una excepción
        return null;
    }

    @Transactional
    public void delete(Long id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Curso no encontrado con ID: " + id));

        // 1. Desvincular las Lecciones del curso
        if (curso.getLecciones() != null) {
            for (Leccion leccion : curso.getLecciones()) {
                leccion.setCurso(null); // Establece la FK a NULL
                leccionRepository.save(leccion); // Guarda el cambio en la lección
            }
        }

        // 2. Desvincular los Asesores del curso
        if (curso.getAsesores() != null) {
            for (Asesor asesor : curso.getAsesores()) {
                asesor.setCurso(null); // Establece la FK a NULL
                asesorRepository.save(asesor); // Guarda el cambio en el asesor
            }
        }

        // 3. Desvincular los ProgresoCursoUsuario del curso
        if (curso.getProgresos() != null) { // Asegúrate de que 'progresos' esté en tu entidad Curso
            for (ProgresoCursoUsuario progreso : curso.getProgresos()) {
                progreso.setCurso(null); // Establece la FK a NULL
                progresoCursoUsuarioRepository.save(progreso); // Guarda el cambio en el progreso
            }
        }

        // 4. Ahora puedes eliminar el Curso de forma segura
        cursoRepository.delete(curso);
    }



    @Override
    public Curso buscarPorId(Long id) {
        if (cursoRepository.findById(id).isPresent()) {
            return cursoRepository.findById(id).get();
        }
        return null;
    }

    @Override
    public List<LeccionDto> obtenerLeccionesPorCursoId(Long cursoId) {
        return List.of();
    }

    @Override
    public CursoDto buscarCursoConAsesoresPorId(Long id) {
        Optional<Curso> optionalCurso = cursoRepository.findByIdWithAsesores(id);
        if (optionalCurso.isPresent()) {
            Curso curso = optionalCurso.get();
            // Mapear la entidad Curso a CursoDto, incluyendo la lista de Asesores
            return modelMapper.map(curso, CursoDto.class);
        }
        return null; // O lanzar una excepción si el curso no se encuentra
    }
}

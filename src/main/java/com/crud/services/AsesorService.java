package com.crud.services;

import com.crud.dtos.AsesorDto;
import com.crud.entities.Asesor;
import com.crud.interfaces.IAsesorService;
import com.crud.repositorios.AsesorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AsesorService implements IAsesorService {

    @Autowired
    private AsesorRepository asesorRepository;
    @Autowired
    private ModelMapper modelMapper; // Inject ModelMapper

    @Override
    public Asesor grabarAsesor(Asesor asesor) {
        return asesorRepository.save(asesor);
    }

    @Override
    public List<Asesor> listarAsesores() {
        return asesorRepository.findAll();
    }

    @Override
    public AsesorDto editar(Asesor asesor) {
        if (asesorRepository.findById(asesor.getId()).isPresent()) {
            Asesor updatedAsesor = asesorRepository.save(asesor);
            return modelMapper.map(updatedAsesor, AsesorDto.class); // Map to DTO
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        if (asesorRepository.existsById(id)) {
            asesorRepository.deleteById(id);
        }

    }

    @Override
    public Asesor buscarPorId(Long id) {

        if (asesorRepository.findById(id).isPresent()) {
            return asesorRepository.findById(id).get();
        }
        return null;
    }
}

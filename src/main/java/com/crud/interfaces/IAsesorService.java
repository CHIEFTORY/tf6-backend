package com.crud.interfaces;

import com.crud.dtos.AsesorDto;
import com.crud.entities.Asesor;



import java.util.List;

public interface IAsesorService {
    public Asesor grabarAsesor(Asesor asesor);

    public List<Asesor> listarAsesores();

    public AsesorDto editar(Asesor asesor);

    public void delete(Long id);

    Asesor buscarPorId(Long id);
}

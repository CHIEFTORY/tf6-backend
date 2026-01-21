package com.crud.services;

import com.crud.dtos.RankingDto;
import com.crud.entities.Ranking;
import com.crud.interfaces.IRankingService;
import com.crud.repositorios.RankingRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class RankingService implements IRankingService {

    @Autowired
    public RankingRepositorio rankingRepositorio;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Ranking gravarRanking(Ranking ranking) {
        return rankingRepositorio.save(ranking);
    }

    @Override
    public List<Ranking> listRanking() {
        return rankingRepositorio.findAll();
    }

    @Override
    public Ranking editar(Ranking ranking) {
       if (rankingRepositorio.findById(ranking.getId()).isPresent()) {
           return rankingRepositorio.save(ranking);
       }
       return null;
    }

    @Override
    public List<RankingDto> findAllOrderByPuntosDesc() {
        List<Ranking> rankingEntidades = rankingRepositorio.findAllByOrderByPuntosDesc();
        return rankingEntidades.stream().map(r -> modelMapper.map(r, RankingDto.class)).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        if (rankingRepositorio.existsById(id)){
            rankingRepositorio.deleteById(id);
        }
    }
}

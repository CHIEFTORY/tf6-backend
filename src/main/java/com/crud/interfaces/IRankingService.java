package com.crud.interfaces;



import com.crud.dtos.RankingDto;
import com.crud.entities.Ranking;

import java.util.List;

public interface IRankingService {
    public Ranking gravarRanking(Ranking ranking);

    public List<Ranking> listRanking();

    public Ranking editar(Ranking ranking);

    public List<RankingDto> findAllOrderByPuntosDesc();

    public void delete(Long id);
}

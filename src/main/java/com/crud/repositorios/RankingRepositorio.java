package com.crud.repositorios;


import com.crud.entities.Ranking;
import com.crud.security.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RankingRepositorio extends JpaRepository<Ranking, Long> {
    public List<Ranking> findAllByOrderByPuntosDesc();
    Optional<Ranking> findByUser(User user);

}

package com.crud.controllers;


import com.crud.dtos.LeccionDto;
import com.crud.dtos.RankingDto;
import com.crud.entities.Leccion;
import com.crud.entities.Ranking;
import com.crud.services.RankingService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j

@RestController
@CrossOrigin(origins = "${ip.frontend}", allowCredentials = "true", exposedHeaders = "Authorization")
@RequestMapping("/api")
public class RankingController {
    @Autowired
    private RankingService rankingService;

    @PostMapping("/crearRanking")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Ranking> crearRanking(@RequestBody Ranking ranking) {
        Ranking ranking1 = rankingService.gravarRanking(ranking);
        return new ResponseEntity<>(ranking1, HttpStatus.OK);

    }

    @GetMapping("/listarRanking")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public List<RankingDto> listarRanking() {
        List<Ranking> rankings =rankingService.listRanking();
        ModelMapper mapper = new ModelMapper();
        return rankings.stream()
                .map(r -> mapper.map(r, RankingDto.class))
                .collect(Collectors.toList());
    }

    @PutMapping("/editarRanking")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RankingDto>  editarRanking(@RequestBody RankingDto rankingDto) {
        ModelMapper modelMapper = new ModelMapper();
        Ranking ranking = modelMapper.map(rankingDto, Ranking.class);
        ranking = rankingService.editar(ranking);
        rankingDto = modelMapper.map(ranking, RankingDto.class);
        return ResponseEntity.ok(rankingDto);
    }
    @DeleteMapping("/ranking/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void eliminarRanking(@PathVariable long id) {
        rankingService.delete(id);
    }

    @GetMapping("/Listado")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public List<RankingDto> ListadoRanking() {
        log.info("Lista del rank");
        return rankingService.findAllOrderByPuntosDesc();
    }
}

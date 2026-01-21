package com.crud.dtos;

import com.crud.security.dtos.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RankingDto  {

    Long id;

    int puntos;

    UserDto user;
}

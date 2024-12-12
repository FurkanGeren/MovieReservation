package com.movie.reservation_management_server.service;

import com.movie.reservation_management_server.dto.MovieWithShowTimesDTO;

import java.time.LocalDate;
import java.util.List;

public interface MovieService {
    List<MovieWithShowTimesDTO> getShowTime(LocalDate date);

    MovieWithShowTimesDTO getMovieById(Long id, LocalDate date);
}

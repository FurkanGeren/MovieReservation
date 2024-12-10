package com.movie.movie_management_server.service;

import com.movie.movie_management_server.dto.MovieDTO;
import com.movie.movie_management_server.dto.request.MovieRequestDTO;
import com.movie.movie_management_server.entity.Movie;

import java.util.List;

public interface MovieService {
    List<MovieDTO> getAllMovie();

    void addMovie(MovieRequestDTO movieRequestDTO);

    Movie getMovieById(Long id);
}

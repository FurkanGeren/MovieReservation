package com.movie.movie_management_server.service;

import com.movie.movie_management_server.dto.GenreDTO;
import com.movie.movie_management_server.dto.request.GenreRequestDTO;
import com.movie.movie_management_server.entity.Genre;

import java.util.List;
import java.util.Set;

public interface GenreService {
    Set<Genre> getGenresByIds(List<Long> ids);

    void addGenre(GenreRequestDTO genreRequestDTO);

    List<GenreDTO> getAllGenre();
}

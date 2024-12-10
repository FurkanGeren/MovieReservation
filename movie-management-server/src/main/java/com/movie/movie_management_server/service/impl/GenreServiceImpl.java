package com.movie.movie_management_server.service.impl;

import com.movie.movie_management_server.dto.GenreDTO;
import com.movie.movie_management_server.dto.request.GenreRequestDTO;
import com.movie.movie_management_server.entity.Genre;
import com.movie.movie_management_server.repository.GenreRepository;
import com.movie.movie_management_server.service.GenreService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }


    @Override
    public Set<Genre> getGenresByIds(List<Long> ids) {
        List<Genre> genres = genreRepository.findAllById(ids);
        return new HashSet<>(genres);
    }

    @Override
    public void addGenre(GenreRequestDTO genreRequestDTO) {
        Genre genre = Genre.builder()
                .genreName(genreRequestDTO.getGenreName())
                .build();
        genreRepository.save(genre);
    }

    @Override
    public List<GenreDTO> getAllGenre() {
        List<Genre> genres = genreRepository.findAll();
        return genres.stream()
                .map(genre -> GenreDTO.builder()
                        .id(genre.getId())
                        .name(genre.getGenreName())
                        .build()).toList();
    }
}

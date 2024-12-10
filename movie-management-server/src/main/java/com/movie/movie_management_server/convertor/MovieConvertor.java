package com.movie.movie_management_server.convertor;

import com.movie.movie_management_server.dto.MovieDTO;
import com.movie.movie_management_server.entity.Genre;
import com.movie.movie_management_server.entity.Movie;
import lombok.experimental.UtilityClass;

import java.util.Base64;
import java.util.stream.Collectors;

@UtilityClass
public class MovieConvertor {


    public MovieDTO toDTO(Movie movie){
        return MovieDTO.builder()
                .movieTitle(movie.getMovieTitle())
                .description(movie.getDescription())
                .image(Base64.getEncoder().encodeToString(movie.getImage().getBytes()))
                .genres(movie.getGenres().stream()
                        .map(Genre::getGenreName)
                        .collect(Collectors.toList()))
                .build();
    }
}

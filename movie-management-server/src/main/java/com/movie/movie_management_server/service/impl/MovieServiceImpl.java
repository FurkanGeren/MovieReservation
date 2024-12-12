package com.movie.movie_management_server.service.impl;

import com.movie.movie_management_server.convertor.MovieConvertor;
import com.movie.movie_management_server.dto.MovieDTO;
import com.movie.movie_management_server.dto.request.MovieRequestDTO;
import com.movie.movie_management_server.entity.Genre;
import com.movie.movie_management_server.entity.Image;
import com.movie.movie_management_server.entity.Movie;
import com.movie.movie_management_server.repository.MovieRepository;
import com.movie.movie_management_server.service.GenreService;
import com.movie.movie_management_server.service.MovieService;
import com.movie.movie_management_server.service.ShowTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {


    private final MovieRepository movieRepository;
    private final GenreService genreService;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, GenreService genreService) {
        this.movieRepository = movieRepository;
        this.genreService = genreService;
    }


    @Override
    public List<MovieDTO> getAllMovie() {
        List<Movie> movies = movieRepository.findAll();
        return movies.stream().map(movie -> MovieDTO.builder()
                        .movieTitle(movie.getMovieTitle())
                        .description(movie.getDescription())
                        .image(Base64.getEncoder().encodeToString(movie.getImage().getBytes()))
                        .genres(movie.getGenres().stream()
                                .map(Genre::getGenreName)
                                .collect(Collectors.toList()))
                .build()
                ).toList();
    }

    @Override
    public void addMovie(MovieRequestDTO movieRequestDTO) {

        Set<Genre> genres = genreService.getGenresByIds(movieRequestDTO.getGenresId());

        Movie movie = Movie.builder()
                .movieTitle(movieRequestDTO.getMovieTitle())
                .description(movieRequestDTO.getDescription())
                .genres(genres)
                .build();

//        Set<Image> images = movieRequestDTO.getImageBytes().stream()
//                .map(image -> Image.builder()
//                        .bytes(image.getBytes())
//                        .movie(movie)
//                        .build())
//                .collect(Collectors.toSet());

        Image image = Image.builder()
                .bytes(movieRequestDTO.getImageByte().getBytes())
                .movie(movie)
                .build();

        // Set the images to the movie entity
        movie.setImage(image);

        movieRepository.save(movie);
    }

    @Override
    public Movie getMovieById(Long id){
        return movieRepository.findById(id).orElse(null);
    }

    @Override
    public MovieDTO getMovieDTOById(Long id) {
        Movie movie = movieRepository.findById(id).orElseThrow(); // TODO
        return MovieConvertor.toDTO(movie);
    }
}

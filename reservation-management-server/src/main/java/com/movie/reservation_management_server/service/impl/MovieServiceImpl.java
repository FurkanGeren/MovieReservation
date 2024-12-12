package com.movie.reservation_management_server.service.impl;

import com.movie.reservation_management_server.dto.MovieWithShowTimesDTO;
import com.movie.reservation_management_server.dto.ShowTimeDTO;
import com.movie.reservation_management_server.entity.Genre;
import com.movie.reservation_management_server.entity.Movie;
import com.movie.reservation_management_server.repository.MovieRepository;
import com.movie.reservation_management_server.service.MovieService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }


    @Override
    public List<MovieWithShowTimesDTO> getShowTime(LocalDate date) {
        List<Movie> movies = movieRepository.findAllByShowDate(date);
        return movies.stream().map(movie -> getMovieWithShowTimesDTO(movie, date)).toList();
    }


    @Override
    public MovieWithShowTimesDTO getMovieById(Long id, LocalDate date) {
        Movie movie = movieRepository.findByShowDateAndMovieId(date, id);
        return  getMovieWithShowTimesDTO(movie, date);
    }


    private MovieWithShowTimesDTO getMovieWithShowTimesDTO(Movie movie, LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return MovieWithShowTimesDTO.builder()
                .movieId(movie.getId())
                .movieTitle(movie.getMovieTitle())
                .description(movie.getDescription())
                .genres(movie.getGenres().stream()
                        .map(Genre::getGenreName)
                        .toList()
                )
                .imageByte(movie.getImage() != null ?
                        Base64.getEncoder().encodeToString(movie.getImage().getBytes()) : null)
                .showTimes(movie.getShowTimes().stream()
                        .filter(showtime -> showtime.getShowDate().isEqual(date))
                        .map(showtime -> ShowTimeDTO.builder()
                                .time(showtime.getShowTime().format(formatter))
                                .showTimeId(showtime.getId())
                                .build()
                        ).toList()
                )
                .build();
    }
}

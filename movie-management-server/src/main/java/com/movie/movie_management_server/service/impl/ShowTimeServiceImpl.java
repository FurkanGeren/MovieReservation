package com.movie.movie_management_server.service.impl;

import com.movie.movie_management_server.convertor.CapacityJsonConverter;
import com.movie.movie_management_server.convertor.MovieConvertor;
import com.movie.movie_management_server.dto.ShowTimeDTO;
import com.movie.movie_management_server.dto.request.ShowTimeRequestDTO;
import com.movie.movie_management_server.entity.Movie;
import com.movie.movie_management_server.entity.Showtime;
import com.movie.movie_management_server.repository.ShowTimeRepository;
import com.movie.movie_management_server.service.MovieService;
import com.movie.movie_management_server.service.ShowTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ShowTimeServiceImpl implements ShowTimeService {

    private final ShowTimeRepository showTimeRepository;
    private final CapacityJsonConverter capacityJsonConverter;
    private final MovieService movieService;

    @Autowired
    public ShowTimeServiceImpl(ShowTimeRepository showTimeRepository, CapacityJsonConverter capacityJsonConverter, MovieService movieService) {
        this.showTimeRepository = showTimeRepository;
        this.capacityJsonConverter = capacityJsonConverter;
        this.movieService = movieService;
    }


    @Override
    public void addShowTime(ShowTimeRequestDTO showTimeRequestDTO) {
        List<Integer> capacities = capacityJsonConverter.generateCapacityList(showTimeRequestDTO.getCapacity());

        Movie movie = getMovieById(showTimeRequestDTO.getMovieId());

        Showtime showtime = Showtime.builder()
                .showTime(showTimeRequestDTO.getTime())
                .capacityTotal(showTimeRequestDTO.getCapacity())
                .capacity(capacities)
                .showDate(showTimeRequestDTO.getDate())
                .movie(movie)
                .build();

        showTimeRepository.save(showtime);
    }

    @Override
    public List<ShowTimeDTO> getShowTime(LocalDate date) {
        List<Showtime> showtimes = showTimeRepository.findAllByShowDate(date);
        return showtimes.stream()
                .map(showtime -> ShowTimeDTO.builder()
                        .id(showtime.getId())
                        .movie(MovieConvertor.toDTO(showtime.getMovie()))
                        .time(showtime.getShowTime())
                        .date(showtime.getShowDate())
                        .capacity(showtime.getCapacity())
                        .build()).toList();
    }

    @Override
    public ShowTimeDTO getShowTimeById(Long id) {
        Showtime showtime = showTimeRepository.findById(id).orElseThrow(); // TODO

        return ShowTimeDTO.builder()
                .id(showtime.getId())
                .movie(MovieConvertor.toDTO(showtime.getMovie()))
                .capacity(showtime.getCapacity())
                .time(showtime.getShowTime())
                .date(showtime.getShowDate())
                .build();
    }

    private Movie getMovieById(Long movieId) {
        return movieService.getMovieById(movieId);
    }
}

package com.movie.reservation_management_server.controller;

import com.movie.reservation_management_server.dto.MovieWithShowTimesDTO;
import com.movie.reservation_management_server.service.MovieService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/movie")
@Log4j2
public class MovieController {

    private final MovieService movieService;


    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }


    @GetMapping("/all")
    public ResponseEntity<List<MovieWithShowTimesDTO>> getAllShowTimes(@RequestParam(value = "date", required = false) String dateStr){
        LocalDate date = (dateStr == null || dateStr.isEmpty()) ? LocalDate.now() : LocalDate.parse(dateStr);
        log.info("Date {}", date);
        return ResponseEntity.ok(movieService.getShowTime(date));
    }


    @GetMapping("/{id}")
    public ResponseEntity<MovieWithShowTimesDTO> getMovieById(@PathVariable("id") Long id, @RequestParam(value = "date", required = false) String dateStr){
        LocalDate date = (dateStr == null || dateStr.isEmpty()) ? LocalDate.now() : LocalDate.parse(dateStr);
        return ResponseEntity.ok(movieService.getMovieById(id, date));
    }

}

package com.movie.movie_management_server.controller;

import com.movie.movie_management_server.dto.MovieDTO;
import com.movie.movie_management_server.dto.request.MovieRequestDTO;
import com.movie.movie_management_server.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movie")
public class MovieController {

    private final MovieService movieService;


    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }


    @GetMapping("/all")
    public ResponseEntity<List<MovieDTO>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMovie());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addMovie(@Valid @RequestBody MovieRequestDTO movieRequestDTO) {
        movieService.addMovie(movieRequestDTO);
        return ResponseEntity.ok("Movie added successfully");
    }




}

package com.movie.movie_management_server.controller;

import com.movie.movie_management_server.dto.GenreDTO;
import com.movie.movie_management_server.dto.request.GenreRequestDTO;
import com.movie.movie_management_server.entity.Genre;
import com.movie.movie_management_server.service.GenreService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/genre")
public class GenreController {


    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addGenre(@Valid @RequestBody GenreRequestDTO genreRequestDTO) {
        genreService.addGenre(genreRequestDTO);
        return ResponseEntity.ok("Genre added successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<List<GenreDTO>> getAllGenre() {
        return ResponseEntity.ok(genreService.getAllGenre());
    }
}

package com.movie.movie_management_server.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GenreRequestDTO {

    @NotBlank(message = "Genre name cannot be blank.")
    private String genreName;
}

package com.movie.movie_management_server.dto.request;


import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MovieRequestDTO {

    @NotBlank(message = "Movie title cannot be blank.")
    private String movieTitle;

    @NotBlank(message = "Description cannot be blank.")
    private String description;

    @NotEmpty(message = "Genres list cannot be empty.")
    private List<Long> genresId;

    @NotBlank(message = "Image byte data cannot be blank.")
    private String imageByte;
}

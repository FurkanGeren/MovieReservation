package com.movie.movie_management_server.dto.request;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GenreRequestDTO {

    private String genreName;
}

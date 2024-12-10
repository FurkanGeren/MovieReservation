package com.movie.movie_management_server.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MovieDTO {

    private String movieTitle;

    private String description;

    private List<String> genres;

    private String image;

}

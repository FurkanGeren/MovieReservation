package com.movie.movie_management_server.dto.request;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MovieRequestDTO {

    private String movieTitle;

    private String description;

    private List<Long> genresId;

    private String imageByte;
}

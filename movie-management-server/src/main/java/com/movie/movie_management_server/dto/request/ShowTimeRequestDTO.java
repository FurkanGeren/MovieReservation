package com.movie.movie_management_server.dto.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class ShowTimeRequestDTO {

    private Long movieId;

    private LocalDate date;

    private LocalTime time;

    private Integer capacity;

}

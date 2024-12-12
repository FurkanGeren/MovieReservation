package com.movie.movie_management_server.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
public class ShowTimeDTO {

    private Long id;

    private MovieDTO movie;

    private LocalTime time;

    private LocalDate date;

    private List<Integer> capacity;

}

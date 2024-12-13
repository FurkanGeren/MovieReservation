package com.movie.movie_management_server.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class ShowTimeRequestDTO {

    @NotNull(message = "Movie cannot be null.")
    private Long movieId;

    @NotNull(message = "Date cannot be null.")
    @FutureOrPresent(message = "Date must be in the present or future.")
    private LocalDate date;

    @NotNull(message = "Time cannot be null.")
    private LocalTime time;

    @NotNull(message = "Capacity cannot be null.")
    @Min(value = 1, message = "Capacity must be at least 1.")
    private Integer capacity;


}

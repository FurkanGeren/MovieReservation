package com.movie.reservation_management_server.dto;


import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieWithShowTimesDTO {

    private Long movieId;

    private String movieTitle;

    private String description;

    private List<String> genres;

    private String imageByte;

    private List<ShowTimeDTO> showTimes;
}

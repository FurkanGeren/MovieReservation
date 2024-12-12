package com.movie.movie_management_server.service;

import com.movie.movie_management_server.dto.ShowTimeDTO;
import com.movie.movie_management_server.dto.request.ShowTimeRequestDTO;

import java.time.LocalDate;
import java.util.List;

public interface ShowTimeService {
    void addShowTime(ShowTimeRequestDTO showTimeRequestDTO);

    List<ShowTimeDTO> getShowTime(LocalDate date);

    ShowTimeDTO getShowTimeById(Long id);
}

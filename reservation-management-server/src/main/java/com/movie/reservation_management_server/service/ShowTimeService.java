package com.movie.reservation_management_server.service;

import com.movie.reservation_management_server.dto.ReservationDetailsDTO;
import com.movie.reservation_management_server.dto.ShowTimeDTO;

public interface ShowTimeService {
    ReservationDetailsDTO getReservationDetailsByShowTimeId(Long id);
}

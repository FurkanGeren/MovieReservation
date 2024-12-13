package com.movie.reservation_management_server.service;

import com.movie.reservation_management_server.dto.ReservationDetailsDTO;
import com.movie.reservation_management_server.dto.ShowTimeDTO;
import com.movie.reservation_management_server.entity.Showtime;

public interface ShowTimeService {
    ReservationDetailsDTO getReservationDetailsByShowTimeId(Long id);

    Showtime getShowtime(Long id);

    void removeSeat(Showtime showtime);

    void updateShowTime(Showtime showTime, String seatNumber);

}

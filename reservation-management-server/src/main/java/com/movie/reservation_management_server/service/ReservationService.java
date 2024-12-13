package com.movie.reservation_management_server.service;

import com.movie.reservation_management_server.dto.ReservationDTO;
import com.movie.reservation_management_server.dto.ReservationsForAdminDTO;

import java.util.List;

public interface ReservationService {
    void createReservation(String userId, Long showtimeId, Integer seatNumber);

    List<ReservationDTO> getReservationsByUser(String userId);

    void cancelReservation(String userId, Long reservationId);

    List<ReservationsForAdminDTO> getReservations();
}

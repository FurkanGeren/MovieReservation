package com.movie.reservation_management_server.service;

import com.movie.reservation_management_server.dto.ReservationDTO;
import com.movie.reservation_management_server.dto.ReservationsForAdminDTO;

import java.util.List;

public interface ReservationService {
    void createReservation(String userEmail, Long showtimeId, Integer seatNumber);

    List<ReservationDTO> getReservationsByUser(String userEmail);

    void cancelReservation(String userEmail, Long reservationId);

    List<ReservationsForAdminDTO> getReservations();
}

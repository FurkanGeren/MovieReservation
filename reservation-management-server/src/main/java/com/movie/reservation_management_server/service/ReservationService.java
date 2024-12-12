package com.movie.reservation_management_server.service;

public interface ReservationService {
    void createReservation(String userEmail, Long showtimeId, Integer seatNumber);
}

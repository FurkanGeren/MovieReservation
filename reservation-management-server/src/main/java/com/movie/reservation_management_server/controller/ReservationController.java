package com.movie.reservation_management_server.controller;

import com.movie.reservation_management_server.service.ReservationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservation")
@Log4j2
public class ReservationController {


    private final ReservationService reservationService;


    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }


    @GetMapping("/create")
    public ResponseEntity<?> addReservation(@RequestHeader(value = "X-User-Name") String userEmail,
                                            @RequestParam Long showtimeId,
                                            @RequestParam Integer seatNumber) {
        log.info("UserId {}", userEmail);
        log.info("Reservation ID: {}, Seat Number: {}", showtimeId, seatNumber);
        reservationService.createReservation(userEmail,showtimeId,seatNumber);

        return ResponseEntity.ok().build();
    }



}

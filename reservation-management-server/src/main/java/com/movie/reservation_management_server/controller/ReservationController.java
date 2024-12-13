package com.movie.reservation_management_server.controller;

import com.movie.reservation_management_server.dto.ReservationDTO;
import com.movie.reservation_management_server.dto.ReservationsForAdminDTO;
import com.movie.reservation_management_server.entity.Reservation;
import com.movie.reservation_management_server.service.ReservationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/get")
    public ResponseEntity<?> addReservation(@RequestHeader(value = "X-User-Name") String userEmail){
        List<ReservationDTO> reservations = reservationService.getReservationsByUser(userEmail);
        if (reservations.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No reservations found for the user.");
        }
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/cancel")
    public ResponseEntity<?> cancelReservation(@RequestHeader(value = "X-User-Name") String userEmail,
                                               @RequestParam Long reservationId){
        reservationService.cancelReservation(userEmail, reservationId);
        return ResponseEntity.ok("Reservation has been cancelled.");
    }

    @GetMapping("/admin/all")
    public ResponseEntity<?> getAllReservations(){
        List<ReservationsForAdminDTO> reservations = reservationService.getReservations();
        if (reservations.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No reservations found for the user.");
        }
        return ResponseEntity.ok(reservations);
    }




}

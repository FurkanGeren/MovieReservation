package com.movie.reservation_management_server.controller;

import com.movie.reservation_management_server.dto.ReservationDetailsDTO;
import com.movie.reservation_management_server.dto.ShowTimeDTO;
import com.movie.reservation_management_server.service.ShowTimeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/show-time")
public class ShowTimeController {

    private final ShowTimeService showTimeService;

    public ShowTimeController(ShowTimeService showTimeService) {
        this.showTimeService = showTimeService;
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<ReservationDetailsDTO> getReservationDetailsByShowTimeId(@PathVariable("id") Long id){
        return ResponseEntity.ok(showTimeService.getReservationDetailsByShowTimeId(id));
    }
}

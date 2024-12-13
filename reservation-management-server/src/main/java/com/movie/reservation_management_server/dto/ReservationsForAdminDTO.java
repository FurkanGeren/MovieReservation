package com.movie.reservation_management_server.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReservationsForAdminDTO {


    private Long movieId;

    private String movieName;

    private String reservationDate;

    private String reservationTime;

    private String seatNumber;

    private String reservationStatus;

    private Long userId;


}

package com.movie.reservation_management_server.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ReservationDetailsDTO {

    private Long reservationId;

    private String movieDate;

    private String movieTime;

    private List<CapacityDTO> capacityDTOS;


}

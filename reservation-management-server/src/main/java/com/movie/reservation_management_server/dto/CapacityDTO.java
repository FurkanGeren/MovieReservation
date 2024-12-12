package com.movie.reservation_management_server.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CapacityDTO {

    private Boolean available;

    private String seatNumber;
}

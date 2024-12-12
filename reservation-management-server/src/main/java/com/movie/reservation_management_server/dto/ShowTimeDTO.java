package com.movie.reservation_management_server.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ShowTimeDTO {

    private Long showTimeId;

    private String time;


}

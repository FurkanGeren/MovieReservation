package com.movie.reservation_management_server.service.impl;

import com.movie.reservation_management_server.dto.CapacityDTO;
import com.movie.reservation_management_server.dto.ReservationDetailsDTO;
import com.movie.reservation_management_server.dto.ShowTimeDTO;
import com.movie.reservation_management_server.entity.Showtime;
import com.movie.reservation_management_server.repository.ShowTimeRepository;
import com.movie.reservation_management_server.service.ShowTimeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
public class ShowTimeServiceImpl implements ShowTimeService {

    private final ShowTimeRepository showTimeRepository;

    public ShowTimeServiceImpl(ShowTimeRepository showTimeRepository) {
        this.showTimeRepository = showTimeRepository;
    }


    @Override
    public ReservationDetailsDTO getReservationDetailsByShowTimeId(Long id) {
        Showtime showtime = showTimeRepository.findById(id).orElseThrow(); // TODO
        List<Integer> availableSeats = showtime.getCapacity();
        List<Integer> totalSeats = getTotalSeatsForShowtime(showtime.getCapacityTotal());
        log.info("Total Seats: {}", totalSeats);
        if (totalSeats.size() > showtime.getCapacityTotal())
            throw new RuntimeException("Total Seats exceeds Capacity"); // TODO

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");


        List<CapacityDTO> capacityDTOS = totalSeats.stream().map(seat -> {
            boolean isAvailable = availableSeats.contains(seat);
            return CapacityDTO.builder()
                    .seatNumber(seat.toString())
                    .available(isAvailable)
                    .build();
        }).toList();

        return ReservationDetailsDTO.builder()
                .movieDate(String.valueOf(showtime.getShowDate()))
                .movieTime(showtime.getShowTime().format(formatter))
                .capacityDTOS(capacityDTOS)
                .build();
    }

    private List<Integer> getTotalSeatsForShowtime(int totalSeats) {
        return IntStream.range(1, totalSeats + 1).boxed().collect(Collectors.toList());
    }
}

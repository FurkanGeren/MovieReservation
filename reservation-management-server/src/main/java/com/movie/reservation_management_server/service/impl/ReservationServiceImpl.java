package com.movie.reservation_management_server.service.impl;

import com.movie.reservation_management_server.entity.Reservation;
import com.movie.reservation_management_server.entity.Showtime;
import com.movie.reservation_management_server.repository.ReservationRepository;
import com.movie.reservation_management_server.service.ReservationService;
import com.movie.reservation_management_server.service.ShowTimeService;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
public class ReservationServiceImpl implements ReservationService {


    private final ReservationRepository reservationRepository;
    private final ShowTimeService showTimeService;

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository, ShowTimeService showTimeService) {
        this.reservationRepository = reservationRepository;
        this.showTimeService = showTimeService;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void createReservation(String userEmail, Long showtimeId, Integer seatNumber) {

        Showtime showtime = showTimeService.getShowtime(showtimeId);
        

        if(!showtime.getCapacity().contains(seatNumber)){
            throw new RuntimeException("Seat is not available."); // TODO
        }
        Reservation reservation = Reservation.builder()
                .seatNumber(String.valueOf(seatNumber))
                .showTime(showtime)
                .userId(userEmail)
                .build();
        showtime.getCapacity().remove(seatNumber);
        log.info("Removed seat {} from capacity", seatNumber);
        showTimeService.removeSeat(showtime);

        reservationRepository.save(reservation);

    }
}

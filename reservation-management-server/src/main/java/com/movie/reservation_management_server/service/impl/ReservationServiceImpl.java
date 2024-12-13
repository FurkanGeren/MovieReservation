package com.movie.reservation_management_server.service.impl;

import com.movie.reservation_management_server.dto.ReservationDTO;
import com.movie.reservation_management_server.dto.ReservationsForAdminDTO;
import com.movie.reservation_management_server.entity.Reservation;
import com.movie.reservation_management_server.entity.ReservationStatus;
import com.movie.reservation_management_server.entity.Showtime;
import com.movie.reservation_management_server.repository.ReservationRepository;
import com.movie.reservation_management_server.service.ReservationService;
import com.movie.reservation_management_server.service.ShowTimeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
                .status(ReservationStatus.ACTIVE.toString())
                .build();
        showtime.getCapacity().remove(seatNumber);
        log.info("Removed seat {} from capacity", seatNumber);
        showTimeService.removeSeat(showtime);

        reservationRepository.save(reservation);
    }

    @Override
    public List<ReservationDTO> getReservationsByUser(String userEmail) {
        //List<Reservation> reservations = reservationRepository.findUpcomingReservationsByUserId(userEmail).orElse(null);
        List<Reservation> reservations = reservationRepository.findAllByUserId(userEmail).orElse(null);
        if(reservations == null)
            return null;

        return reservations.stream().map(
                reservation -> {
                    LocalDateTime reservationDateTime = LocalDateTime.of(
                            reservation.getShowTime().getShowDate(),
                            reservation.getShowTime().getShowTime()
                    );
                    LocalDateTime now = LocalDateTime.now();
                    boolean isExpired = reservationDateTime.isBefore(now);
                    if (isExpired) {
                        setReservationStatus(reservation);
                    }
                    return ReservationDTO.builder()
                            .reservationId(reservation.getId())
                            .reservationDate(reservation.getShowTime().getShowDate().format(DateTimeFormatter.ofPattern("dd MMMM yyyy")))
                            .reservationTime(reservation.getShowTime().getShowTime().format(DateTimeFormatter.ofPattern("HH:mm")))
                            .movieId(reservation.getShowTime().getMovie().getId())
                            .movieName(reservation.getShowTime().getMovie().getMovieTitle())
                            .seatNumber(reservation.getSeatNumber())
                            .reservationStatus(reservation.getStatus())
                            .build();
                }
        ).toList();
    }

    private void setReservationStatus(Reservation reservation){
        reservation.setStatus(ReservationStatus.PASSED.toString());
        reservationRepository.save(reservation);
    }

    @Override
    public void cancelReservation(String userEmail, Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(); // TODO

       if (!reservation.getUserId().equals(userEmail)) {
           throw new RuntimeException("User does not belong to the user email"); // TODO
       }

        LocalDate showDate = reservation.getShowTime().getShowDate();
        LocalTime showTime = reservation.getShowTime().getShowTime();

        if(reservation.getStatus().equals(ReservationStatus.PASSED.toString())){
            throw new RuntimeException("Cannot cancel the reservation because the show date and time has passed");
        } else if (showDate.isBefore(LocalDate.now()) ||
                (showDate.isEqual(LocalDate.now()) && showTime.isBefore(LocalTime.now()))) {
            throw new RuntimeException("Cannot cancel the reservation because the show date and time has passed");
        }
        reservation.setStatus(ReservationStatus.CANCELLED.toString());
        reservationRepository.save(reservation);

        showTimeService.updateShowTime(reservation.getShowTime(),reservation.getSeatNumber());
    }

    @Override
    public List<ReservationsForAdminDTO> getReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream().map(
                reservation -> ReservationsForAdminDTO.builder()
                        .reservationDate(reservation.getShowTime().getShowDate().format(DateTimeFormatter.ofPattern("dd MMMM yyyy")))
                        .reservationTime(reservation.getShowTime().getShowTime().format(DateTimeFormatter.ofPattern("HH:mm")))
                        .movieId(reservation.getShowTime().getMovie().getId())
                        .movieName(reservation.getShowTime().getMovie().getMovieTitle())
                        .seatNumber(reservation.getSeatNumber())
                        .reservationStatus(reservation.getStatus())
                        .userId(Long.valueOf(reservation.getUserId()))
                        .build()
        ).toList();
    }


}

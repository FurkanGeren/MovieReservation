package com.movie.reservation_management_server.repository;

import com.movie.reservation_management_server.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("SELECT r FROM Reservation r " +
            "WHERE r.userId = :userId " +
            "AND (r.showTime.showDate > CURRENT_DATE " +
            "OR (r.showTime.showDate = CURRENT_DATE AND r.showTime.showTime > CURRENT_TIME))")
    Optional<List<Reservation>> findUpcomingReservationsByUserId(@Param("userId") String userId);

    Optional<List<Reservation>> findAllByUserId(String userEmail);
}

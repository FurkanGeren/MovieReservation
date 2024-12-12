package com.movie.reservation_management_server.repository;

import com.movie.reservation_management_server.entity.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowTimeRepository extends JpaRepository<Showtime, Long> {
}

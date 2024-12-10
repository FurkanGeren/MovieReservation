package com.movie.movie_management_server.repository;

import com.movie.movie_management_server.entity.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;

@RequestMapping
public interface ShowTimeRepository extends JpaRepository<Showtime, Long> {
    List<Showtime> findAllByShowDate(LocalDate date);
}

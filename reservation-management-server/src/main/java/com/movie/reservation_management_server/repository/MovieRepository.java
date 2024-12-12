package com.movie.reservation_management_server.repository;

import com.movie.reservation_management_server.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query("SELECT DISTINCT m FROM Movie m " +
            "JOIN m.showTimes s " +
            "WHERE s.showDate = :date")
    List<Movie> findAllByShowDate(@Param("date") LocalDate date);

    @Query("SELECT DISTINCT m FROM Movie m JOIN m.showTimes s WHERE s.showDate >= :date")
    List<Movie> findAllByShowDateAfter(@Param("date") LocalDate date);


    @Query("SELECT DISTINCT m FROM Movie m " +
            "JOIN m.showTimes s " +
            "WHERE s.showDate = :date AND m.id = :movieId")
    Movie findByShowDateAndMovieId(@Param("date") LocalDate date, @Param("movieId") Long movieId);

}

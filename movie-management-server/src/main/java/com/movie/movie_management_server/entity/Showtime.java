package com.movie.movie_management_server.entity;


import com.movie.movie_management_server.convertor.CapacityJsonConverter;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "show_times")
@Builder
public class Showtime extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @Column(name = "show_date", nullable = false)
    private LocalDate showDate;

    @Column(name = "show_time", nullable = false)
    private LocalTime showTime;

    @Convert(converter = CapacityJsonConverter.class)
    @Column(name = "capacity", nullable = false, columnDefinition = "TEXT")
    private List<Integer> capacity;


}

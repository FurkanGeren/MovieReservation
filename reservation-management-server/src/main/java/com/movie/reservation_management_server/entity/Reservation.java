package com.movie.reservation_management_server.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_reservation")
@Builder
public class Reservation extends BaseEntity {


    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "seat_number", nullable = false)
    private String seatNumber;

    @ManyToOne
    @JoinColumn(name = "show_time_id", referencedColumnName = "id", nullable = false)
    private Showtime showTime;

    private String  status;

}

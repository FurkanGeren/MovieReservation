package com.movie.reservation_management_server.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "images")
@Builder
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(length = 500000, name = "image_bytes", nullable = false)
    @Basic(fetch = FetchType.LAZY)
    private byte[] bytes;

    @OneToOne
    @JoinColumn(name = "movie_id", referencedColumnName = "id", nullable = false)
    private Movie movie;


}

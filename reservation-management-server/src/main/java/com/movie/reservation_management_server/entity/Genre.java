package com.movie.reservation_management_server.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "genres")
@Builder
public class Genre extends BaseEntity {

    @Column(name = "genre_name", nullable = false)
    private String genreName;

    @ManyToMany(mappedBy = "genres", fetch = FetchType.LAZY)
    private Set<Movie> movies = new HashSet<>();
}

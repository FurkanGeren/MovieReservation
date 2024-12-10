package com.movie.movie_management_server.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movies")
@Builder
public class Movie extends BaseEntity {

    @Column(name = "movie_title", nullable = false)
    private String movieTitle;

    @Column(name = "movie_description", nullable = false)
    private String description;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "movie_genres",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres;

    @OneToOne(mappedBy = "movie", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private Image image;
}

package com.cdw_ticket.movie_service.entity;

import com.cdw_ticket.movie_service.enums.MovieStatus;
import com.cdw_ticket.movie_service.enums.Rating;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    String id;

    @Column(nullable = false)
    String title;

    @Column(name = "description", columnDefinition = "TEXT")
    String description;

    @Column(nullable = false)
    int duration;

    @Column(name = "release_date")
    @Temporal(TemporalType.DATE)
    Date releaseDate;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "movie_genre",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    @Builder.Default
    Set<Genre> genres = new HashSet<>();

    @Column(name = "director")
    String director;

    @ElementCollection
    @CollectionTable(
            name = "movie_casts",
            joinColumns = @JoinColumn(name = "movie_id"))
    @Column(name = "casts")
    List<String> casts;

    @Enumerated(EnumType.STRING)
    Rating rating;

    @Column(name = "poster_url")
    String posterUrl;

    @Column(name = "trailer_url")
    String trailerUrl;

    @Column(name = "language")
    String language;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    MovieStatus status;

    @Column(name = "subtitle")
    String subtitle;
}

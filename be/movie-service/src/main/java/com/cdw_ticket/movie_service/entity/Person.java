package com.cdw_ticket.movie_service.entity;

import com.cdw_ticket.movie_service.enums.PersonRole;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "genres")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    @Column(name = "name")
    String name;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    PersonRole role;

    @OneToMany(mappedBy = "director", fetch = FetchType.LAZY)
    Set<Movie> directedMovies = new HashSet<>();

    @ManyToMany(mappedBy = "casts", fetch = FetchType.LAZY)
    Set<Movie> actedMovies = new HashSet<>();
}

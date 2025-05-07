package com.cdw_ticket.movie_service.entity;

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
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    @Column(name = "name")
    String name;
    @Column(name = "description")
    String description;

    @ManyToMany(mappedBy = "genres", fetch = FetchType.LAZY)
    Set<Movie> movies = new HashSet<>();
}

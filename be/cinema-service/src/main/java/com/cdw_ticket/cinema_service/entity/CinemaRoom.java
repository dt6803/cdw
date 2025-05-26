package com.cdw_ticket.cinema_service.entity;

import com.cdw_ticket.cinema_service.enums.RoomType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "rooms")
public class CinemaRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(nullable = false)
    String name;

    @Enumerated(EnumType.STRING)
    RoomType type;

    @Column(name = "capacity")
    int capacity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cinema_id")
    Cinema cinema;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    List<Seat> seats = new ArrayList<>();
}

package com.cdw_ticket.cinema_service.entity;

import com.cdw_ticket.cinema_service.enums.SeatType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "seats")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(name = "row_code") // A, B, C...
    String rowCode;

    @Column(name = "seat_number") // 1, 2, 3...
    int seatNumber;

    @Enumerated(EnumType.STRING)
    SeatType type; // NORMAL, VIP, COUPLE

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    CinemaRoom room;
}

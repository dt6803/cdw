package com.cdw_ticket.booking_service.entity;

import com.cdw_ticket.booking_service.enums.SeatType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "booking_seats")
public class BookingSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false)
    Booking booking;

    @Column(name = "seat_id", nullable = false)
    String seatId;

    @Column(name = "seat_code", nullable = false)
    String seatCode;

    @Column(name = "type", nullable = false)
    SeatType type;

    @Column(name = "price", nullable = false)
    BigDecimal price;
}

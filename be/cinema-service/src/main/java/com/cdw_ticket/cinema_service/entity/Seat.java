package com.cdw_ticket.cinema_service.entity;

import com.cdw_ticket.cinema_service.enums.SeatStatus;
import com.cdw_ticket.cinema_service.enums.SeatType;
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
@Table(name = "seats")
public class Seat {
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        String id;
    
        @Column(name = "seat_code") // A, B, C...
        String seatCode;
    
        @Column(name = "row_number") // 1, 2, 3...
        int rowNumber;
    
        @Column(name = "col_number") // 1, 2, 3...
        int colNumber;
    
        @Enumerated(EnumType.STRING)
        SeatType type; // NORMAL, VIP, COUPLE
    
        @Column(name = "price")
        BigDecimal price;
    
        @Column(name = "status")
        @Enumerated(EnumType.STRING)
        SeatStatus status;
    
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "room_id")
        CinemaRoom room;
}

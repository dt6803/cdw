package com.cdw_ticket.booking_service.entity;

import com.cdw_ticket.booking_service.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    @Column(name = "user_id", nullable = false)
    String userId;

    @Column(name = "showtime_id", nullable = false)
    String showtimeId;

    @Column(name = "total_price")
    BigDecimal totalPrice;

    @Column(name = "payment_id")
    String paymentId;

    @Enumerated(EnumType.STRING)
    BookingStatus status;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    LocalDateTime createdAt;

    @Builder.Default
    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    List<BookingSeat> bookingSeats = new ArrayList<>();
}

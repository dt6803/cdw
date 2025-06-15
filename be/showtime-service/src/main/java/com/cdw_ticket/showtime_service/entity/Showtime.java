package com.cdw_ticket.showtime_service.entity;

import com.cdw_ticket.showtime_service.enums.ShowtimeStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "show_times")
public class Showtime {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(name = "movie_id", nullable = false)
    String movieId;

    @Column(name = "cinema_id", nullable = false)
    String cinemaId;

    @Column(name = "room_id", nullable = false)
    String roomId;

    @Column(name = "start_time", nullable = false)
    LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    LocalDateTime endTime;

    @Column(nullable = false)
    BigDecimal price;

    @Enumerated(EnumType.STRING)
    ShowtimeStatus status;
}

package com.cdw_ticket.cinema_service.dto.request;

import com.cdw_ticket.cinema_service.entity.CinemaRoom;
import com.cdw_ticket.cinema_service.enums.SeatType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SeatRequest {
    @NotBlank(message = "Seat's row must be not blank")
    String rowCode;

    @NotNull(message = "Seat's number must be not null")
    int seatNumber;

    SeatType type; // NORMAL, VIP, COUPLE
    @NotBlank(message = "Seat's cinemaid must be not blank")
    String cinemaId;
}

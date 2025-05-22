package com.cdw_ticket.cinema_service.dto.response;

import com.cdw_ticket.cinema_service.entity.CinemaRoom;
import com.cdw_ticket.cinema_service.enums.SeatType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SeatResponse {
    String id;
    String rowCode;
    int seatNumber;
    SeatType type; // NORMAL, VIP, COUPLE
}

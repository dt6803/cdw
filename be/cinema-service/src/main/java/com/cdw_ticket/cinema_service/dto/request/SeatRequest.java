package com.cdw_ticket.cinema_service.dto.request;

import com.cdw_ticket.cinema_service.enums.SeatStatus;
import com.cdw_ticket.cinema_service.enums.SeatType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SeatRequest {
    String seatCode;
    int rowNumber;
    int colNumber;
    SeatType type; // NORMAL, VIP, COUPLE
    BigDecimal price;
    SeatStatus status;
}

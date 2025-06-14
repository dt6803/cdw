package com.cdw_ticket.booking_service.dto.response;

import com.cdw_ticket.booking_service.enums.SeatStatus;
import com.cdw_ticket.booking_service.enums.SeatType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SeatResponse {
    String id;
    String seatCode;
    SeatType type; // NORMAL, VIP, COUPLE
    int rowNumber;
    int colNumber;
    BigDecimal price;
    SeatStatus status;
}

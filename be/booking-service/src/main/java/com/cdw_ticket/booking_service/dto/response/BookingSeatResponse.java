package com.cdw_ticket.booking_service.dto.response;

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
public class BookingSeatResponse {
    String seatId;
    String seatCode;
    SeatType seatType;
    BigDecimal price;
}

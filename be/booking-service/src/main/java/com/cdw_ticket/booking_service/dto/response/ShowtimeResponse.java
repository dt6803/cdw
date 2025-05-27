package com.cdw_ticket.booking_service.dto.response;

import com.cdw_ticket.booking_service.enums.ShowtimeStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShowtimeResponse {
    String id;
    String movieId;
    String cinemaId;
    LocalDateTime startTime;
    LocalDateTime endTime;
    BigDecimal price;
    ShowtimeStatus status;
}

package com.cdw_ticket.payment_service.dto.response;

import com.cdw_ticket.payment_service.enums.BookingStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingResponse {
    String id;
    String showtimeId;
    String userId;
    String movieTitle;
    String cinemaName;
    LocalDateTime showtime;
    BigDecimal totalPrice;
    BookingStatus status;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}

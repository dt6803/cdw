package com.cdw_ticket.booking_service.dto.request;

import com.cdw_ticket.booking_service.entity.BookingSeat;
import com.cdw_ticket.booking_service.enums.BookingStatus;
import com.cdw_ticket.booking_service.enums.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class BookingUserRequest {
    @NotBlank(message = "User's id must be not blank")
    String userId;
    @NotBlank(message = "Showtime's must be not blank")
    String showtimeId;
    @NotEmpty(message = "Seat's ids must be not empty")
    List<String> seatIds;
    @NotNull(message = "Payment method must be not null")
    PaymentMethod paymentMethod;
}

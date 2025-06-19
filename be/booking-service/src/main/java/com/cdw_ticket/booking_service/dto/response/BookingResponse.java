package com.cdw_ticket.booking_service.dto.response;

import com.cdw_ticket.booking_service.entity.BookingSeat;
import com.cdw_ticket.booking_service.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

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
    List<BookingSeatResponse> bookingSeats;
    String urlPayment;
    String qrCodeBase64;
}

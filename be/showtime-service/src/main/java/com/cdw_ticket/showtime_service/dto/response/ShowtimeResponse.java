package com.cdw_ticket.showtime_service.dto.response;

import com.cdw_ticket.showtime_service.enums.ShowtimeStatus;
import jakarta.validation.constraints.NotBlank;
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
    String movieTitle;
    String cinemaId;
    String roomId;
    String cinemaName;
    LocalDateTime startTime;
    LocalDateTime endTime;
    BigDecimal price;
    ShowtimeStatus status;
}

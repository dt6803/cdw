package com.cdw_ticket.showtime_service.dto.request;

import com.cdw_ticket.showtime_service.enums.ShowtimeStatus;
import com.cdw_ticket.showtime_service.validation.ValidShowtimeTime;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@ValidShowtimeTime
public class ShowtimeRequest {
    @NotBlank(message = "Movie's id must be not blank")
    @NotNull(message = "Movie's id must be null")
    String movieId;
    @NotBlank(message = "Cinema's id must be not blank")
    @NotNull(message = "Cinema's id must be null")
    String cinemaId;
    @NotNull(message = "Start time must not be null")
    LocalDateTime startTime;
    @NotNull(message = "End time must not be null")
    LocalDateTime endTime;
    BigDecimal price;
    ShowtimeStatus status;
}

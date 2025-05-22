package com.cdw_ticket.cinema_service.dto.request;

import com.cdw_ticket.cinema_service.entity.Cinema;
import com.cdw_ticket.cinema_service.entity.Seat;
import com.cdw_ticket.cinema_service.enums.RoomType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CinemaRoomRequest {
    @NotBlank(message = "Room's name must be not blank")
    String name;
    RoomType type;
    @NotNull(message = "Cinema room's capacity must be not null")
    int capacity;

    String cinemaId;
}

package com.cdw_ticket.cinema_service.dto.response;

import com.cdw_ticket.cinema_service.enums.RoomType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CinemaRoomResponse {
    String id;
    String name;
    RoomType type;
    int capacity;
    List<SeatResponse> seats;
}

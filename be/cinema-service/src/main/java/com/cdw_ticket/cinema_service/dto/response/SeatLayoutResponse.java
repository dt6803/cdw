package com.cdw_ticket.cinema_service.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SeatLayoutResponse {
    String roomId;
    int total;
    List<SeatResponse> seats;
}

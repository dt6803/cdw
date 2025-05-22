package com.cdw_ticket.cinema_service.dto.response;

import com.cdw_ticket.cinema_service.entity.CinemaBrand;
import com.cdw_ticket.cinema_service.entity.CinemaRoom;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CinemaDetailResponse {
    String id;
    String name;
    String address;
    String city;
    CinemaBrand cinemaBrand;
    String description;
    String imageUrl;
    List<CinemaRoom> rooms;
}

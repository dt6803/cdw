package com.cdw_ticket.cinema_service.dto.response;

import com.cdw_ticket.cinema_service.entity.CinemaBrand;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CinemaSimpleResponse {
    String id;
    String name;
    String address;
    String city;
    CinemaBrand cinemaBrand;
    String description;
    String imageUrl;
}

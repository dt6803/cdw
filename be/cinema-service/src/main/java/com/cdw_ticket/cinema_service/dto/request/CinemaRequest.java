package com.cdw_ticket.cinema_service.dto.request;

import com.cdw_ticket.cinema_service.entity.CinemaBrand;
import com.cdw_ticket.cinema_service.entity.CinemaRoom;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
public class CinemaRequest {
    @NotBlank(message = "Cinema's name must be not blank")
    String name;
    @NotBlank(message = "Cinema's address must be not blank")
    String address;
    @NotBlank(message = "Cinema's city must be not blank")
    String city;
    @NotBlank(message = "Brand's id must be not blank")
    String brandId;
    String description;
    String imageUrl;
}

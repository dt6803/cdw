package com.cdw_ticket.cinema_service.dto.response;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ComboResponse {

    String id;

    String name;

    String description;

    BigDecimal price;

    Boolean available;

    String imageUrl;

}

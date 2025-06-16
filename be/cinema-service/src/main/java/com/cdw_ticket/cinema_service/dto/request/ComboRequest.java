package com.cdw_ticket.cinema_service.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ComboRequest {

    String id;

    String name;

    String description;

    BigDecimal price;

    Boolean available;

    String imageUrl;

}

package com.cdw_ticket.cinema_service.dto.request;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CinemaBrandRequest {

    @NotBlank(message = "Brand's name must be not blank")
    String name;
    String logoUrl;
}

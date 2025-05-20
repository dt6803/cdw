package com.cdw_ticket.movie_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GenreRequest {
    @NotBlank(message = "Genre's name must not be blank")
    String name;
    String description;
}

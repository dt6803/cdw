package com.cdw_ticket.authentication_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LogInRequest {
    @NotBlank(message = "Username must be not blank")
    String username;

    @NotBlank(message = "Password must be not blank")
    String password;
}

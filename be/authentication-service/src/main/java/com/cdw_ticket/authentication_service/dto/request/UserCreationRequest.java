package com.cdw_ticket.authentication_service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @NotBlank(message = "User is not blank")
    @Size(min = 3, max = 100, message = "Username must be between 3 and 100 characters")
    String username;
    @NotBlank(message = "Password is not blank")
    @Size(min = 5, max = 100, message = "Password must be between 5 and 100 characters")
    String password;
    @Email(message = "Email format required")
    @NotEmpty(message = "Email not be empty")
    String email;
}

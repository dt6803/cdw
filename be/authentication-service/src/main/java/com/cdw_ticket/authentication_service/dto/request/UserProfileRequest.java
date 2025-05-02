package com.cdw_ticket.authentication_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserProfileRequest {
    @NotBlank(message = "User's id must be not blank")
    String userId;
    @NotBlank(message = "User's id must be not blank")
    String email;
    @NotBlank(message = "Full name must be not blank")
    String fullName;
    @NotBlank(message = "Date of birth must be not blank")
    LocalDate dob;
    @NotBlank(message = "Phone number must be not blank")
    String phoneNumber;
    @NotBlank(message = "Citizen ID must be not blank")
    String citizenId;
    String avatarUrl;
}

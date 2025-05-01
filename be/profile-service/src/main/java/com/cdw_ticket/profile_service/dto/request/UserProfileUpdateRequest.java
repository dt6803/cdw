package com.cdw_ticket.profile_service.dto.request;

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
public class UserProfileUpdateRequest {
    @NotBlank(message = "Full name must be not blank")
    String fullName;
    @NotBlank(message = "Date of birth must be not blank")
    LocalDate dob;
    @NotBlank(message = "Phone number must be not blank")
    String phoneNumber;
    String avatarUrl;
}

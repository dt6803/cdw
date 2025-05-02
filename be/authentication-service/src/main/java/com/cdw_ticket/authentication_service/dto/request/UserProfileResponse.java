package com.cdw_ticket.authentication_service.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserProfileResponse {
    String id;
    String userId;
    String email;
    String fullName;
    LocalDate dob;
    String phoneNumber;
    String citizenId;
    String avatarUrl;
}

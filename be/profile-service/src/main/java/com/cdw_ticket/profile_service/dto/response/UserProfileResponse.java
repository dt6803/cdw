package com.cdw_ticket.profile_service.dto.response;

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
    int id;
    int userId;
    String fullName;
    LocalDate dob;
    String phoneNumber;
    String citizenId;
    String avatarUrl;
}

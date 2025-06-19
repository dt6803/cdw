package com.cdw_ticket.authentication_service.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationResponse {
    String accessToken;
    String refreshToken;
    @Builder.Default
    String message = "Login successfully";
    @Builder.Default
    String status = "Success";
}

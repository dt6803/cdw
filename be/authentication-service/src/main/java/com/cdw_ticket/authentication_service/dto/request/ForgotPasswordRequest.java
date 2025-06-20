package com.cdw_ticket.authentication_service.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ForgotPasswordRequest {
    String email;
    String userId;
}

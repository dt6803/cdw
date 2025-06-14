package com.cdw_ticket.booking_service.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InitPaymentRequest {
    String ipAddress;
    String userId;
    String txnRef;
    long amount;
}

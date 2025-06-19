package com.cdw_ticket.notification_service.dto.request;

import com.cdw_ticket.notification_service.entity.Recipient;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Map;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SendMailRequest {
    Recipient to;
    String subject;
    String htmlContent;
    Map<String, Object> data;
}

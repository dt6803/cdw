package com.cdw_ticket.notification_service.dto.request;

import com.cdw_ticket.notification_service.entity.Recipient;
import com.cdw_ticket.notification_service.entity.Sender;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailRequest {
    Sender sender;
    List<Recipient> to;
    String subject;
    String htmlContent;

    @Override
    public String toString() {
        return "EmailRequest{" +
                "sender=" + sender +
                ", to=" + to +
                ", subject='" + subject + '\'' +
                ", htmlContent='" + htmlContent + '\'' +
                '}';
    }

}

package com.cdw_ticket.notification_service.controller;

import com.cdw_ticket.notification_service.dto.request.SendMailRequest;
import com.cdw_ticket.notification_service.dto.response.BaseResponse;
import com.cdw_ticket.notification_service.dto.response.EmailResponse;
import com.cdw_ticket.notification_service.service.EmailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailController {
    EmailService emailService;

    @PostMapping("/email/send")
    public BaseResponse<EmailResponse> sendMail(@RequestBody SendMailRequest request) {
        return BaseResponse.<EmailResponse>builder()
                .data(emailService.sendMail(request))
                .build();
    }
}

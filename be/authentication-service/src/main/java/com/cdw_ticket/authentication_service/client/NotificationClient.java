package com.cdw_ticket.authentication_service.client;

import com.cdw_ticket.authentication_service.dto.request.SendMailRequest;
import com.cdw_ticket.authentication_service.dto.response.BaseResponse;
import com.cdw_ticket.authentication_service.dto.response.EmailResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "notification-service",
        url = "${app.services.notification-service}")
public interface NotificationClient {
    @PostMapping("/email/send")
    public BaseResponse<EmailResponse> sendMail(@RequestBody SendMailRequest request);
}

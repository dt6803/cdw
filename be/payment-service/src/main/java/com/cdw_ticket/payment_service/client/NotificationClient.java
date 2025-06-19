package com.cdw_ticket.payment_service.client;

import com.cdw_ticket.payment_service.dto.request.SendMailRequest;
import com.cdw_ticket.payment_service.dto.response.BaseResponse;
import com.cdw_ticket.payment_service.dto.response.EmailResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(
        name = "notification-service",
        url = "${app.services.notification-service}")
public interface NotificationClient {
    @PostMapping("/email/send")
    public BaseResponse<EmailResponse> sendMail(@RequestBody SendMailRequest request);
}

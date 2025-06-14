package com.cdw_ticket.booking_service.client;

import com.cdw_ticket.booking_service.dto.request.InitPaymentRequest;
import com.cdw_ticket.booking_service.dto.response.BaseResponse;
import com.cdw_ticket.booking_service.dto.response.InitPaymentResponse;
import com.cdw_ticket.booking_service.dto.response.SeatResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(
        name = "payment-service",
        url = "${app.services.payment-service}")
public interface PaymentClient {
    @GetMapping("/payments/init")
    public BaseResponse<InitPaymentResponse> initPayment(@RequestBody InitPaymentRequest request);
}

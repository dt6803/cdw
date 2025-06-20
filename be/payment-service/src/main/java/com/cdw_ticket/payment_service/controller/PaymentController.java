package com.cdw_ticket.payment_service.controller;

import com.cdw_ticket.payment_service.dto.request.InitPaymentRequest;
import com.cdw_ticket.payment_service.dto.response.BaseResponse;
import com.cdw_ticket.payment_service.dto.response.InitPaymentResponse;
import com.cdw_ticket.payment_service.dto.response.IpnResponse;
import com.cdw_ticket.payment_service.service.IpnHandler;
import com.cdw_ticket.payment_service.service.PaymentService;
import com.cdw_ticket.payment_service.util.RequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/payments")
@Slf4j
public class PaymentController {
    PaymentService paymentService;
    IpnHandler ipnHandler;

    @PostMapping("/init")
    public BaseResponse<InitPaymentResponse> initPayment(@RequestBody InitPaymentRequest request,
                                                         HttpServletRequest httpServletRequest) {
        var ipAddress = RequestUtil.getIpAddress(httpServletRequest);
        request.setIpAddress(ipAddress);
        log.info("Ip Address: {}", ipAddress);
        return BaseResponse.<InitPaymentResponse>builder()
                .data(paymentService.init(request))
                .build();
    }

    @GetMapping("/vnpay_ipn")
    IpnResponse processIpn(@RequestParam Map<String, String> params) {
        log.info("[VNPay Ipn] Params: {}", params);
        return ipnHandler.process(params);
    }
}

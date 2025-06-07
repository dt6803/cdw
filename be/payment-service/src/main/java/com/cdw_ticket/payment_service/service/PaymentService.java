package com.cdw_ticket.payment_service.service;

import com.cdw_ticket.payment_service.dto.request.InitPaymentRequest;
import com.cdw_ticket.payment_service.dto.response.InitPaymentResponse;
import org.springframework.stereotype.Service;

@Service
public interface PaymentService {
    InitPaymentResponse init(InitPaymentRequest request);

}

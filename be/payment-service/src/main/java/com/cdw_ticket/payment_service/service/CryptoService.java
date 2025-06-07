package com.cdw_ticket.payment_service.service;

import org.springframework.stereotype.Service;

@Service
public interface CryptoService {
    String sign(String data);
}

package com.cdw_ticket.notification_service.service;

import com.cdw_ticket.notification_service.dto.request.SendMailRequest;
import com.cdw_ticket.notification_service.dto.response.EmailResponse;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    EmailResponse sendMail(SendMailRequest request);
}

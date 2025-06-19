package com.cdw_ticket.notification_service.service.impl;

import com.cdw_ticket.notification_service.client.EmailClient;
import com.cdw_ticket.notification_service.dto.request.EmailRequest;
import com.cdw_ticket.notification_service.dto.request.SendMailRequest;
import com.cdw_ticket.notification_service.dto.response.EmailResponse;
import com.cdw_ticket.notification_service.entity.Sender;
import com.cdw_ticket.notification_service.exception.AppException;
import com.cdw_ticket.notification_service.exception.ErrorCode;
import com.cdw_ticket.notification_service.service.EmailService;
import com.cdw_ticket.notification_service.service.EmailTemplateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class EmailServiceImpl implements EmailService {
    EmailClient emailClient;
    EmailTemplateService emailTemplateService;
    @NonFinal
    @Value("${brevo.sender-name}")
    String senderName;

    @NonFinal
    @Value("${brevo.sender-email}")
    String senderEmail;

    @NonFinal
    @Value("${brevo.api-key}")
    String apiKey;
    @Override
    public EmailResponse sendMail(SendMailRequest request) {
        String htmlContent = emailTemplateService.buildEmail(request.getHtmlContent(), request.getData());
        log.info(htmlContent);

        EmailRequest emailRequest = EmailRequest.builder()
                .sender(Sender.builder()
                        .name(senderName)
                        .email(senderEmail)
                        .build())
                .to(List.of(request.getTo()))
                .subject(request.getSubject())
                .htmlContent(htmlContent)
                .build();
        try {
            return emailClient.sendMail(apiKey, emailRequest);
        } catch (FeignException exception) {
            log.info("issue: {}", exception.getMessage());
            throw new AppException(ErrorCode.CANNOT_SEND_MAIL);
        }
    }
}

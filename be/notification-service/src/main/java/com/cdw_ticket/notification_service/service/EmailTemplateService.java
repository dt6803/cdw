package com.cdw_ticket.notification_service.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface EmailTemplateService {
    String buildEmail(String templateName, Map<String, Object> data);
}

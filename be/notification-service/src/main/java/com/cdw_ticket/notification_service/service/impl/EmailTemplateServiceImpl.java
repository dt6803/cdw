package com.cdw_ticket.notification_service.service.impl;

import com.cdw_ticket.notification_service.service.EmailTemplateService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailTemplateServiceImpl implements EmailTemplateService {
    TemplateEngine templateEngine;
    @Override
    public String buildEmail(String templateName, Map<String, Object> data) {
        Context context = new Context();
        context.setVariables(data);
        return templateEngine.process("email/" + templateName, context);
    }
}

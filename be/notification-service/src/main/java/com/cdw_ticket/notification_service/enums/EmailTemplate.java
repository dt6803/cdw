package com.cdw_ticket.notification_service.enums;

public enum EmailTemplate {
    RESET_PASSWORD("reset-password"),
    BOOKING_CONFIRM("booking-confirmation");

    private final String fileName;

    EmailTemplate(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}


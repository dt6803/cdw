package com.cdw_ticket.showtime_service.validation;

import com.cdw_ticket.showtime_service.dto.request.ShowtimeRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;

public class ShowtimeTimeValidator implements ConstraintValidator<ValidShowtimeTime, ShowtimeRequest> {

    @Override
    public boolean isValid(ShowtimeRequest request, ConstraintValidatorContext context) {
        LocalDateTime start = request.getStartTime();
        LocalDateTime end = request.getEndTime();

        boolean valid = true;
        context.disableDefaultConstraintViolation();

        if (start == null || end == null) {
            return true;
        }

        if (start.isBefore(LocalDateTime.now())) {
            context.buildConstraintViolationWithTemplate("Start time must be in the future")
                    .addPropertyNode("startTime")
                    .addConstraintViolation();
            valid = false;
        }

        if (!end.isAfter(start)) {
            context.buildConstraintViolationWithTemplate("End time must be after start time")
                    .addPropertyNode("endTime")
                    .addConstraintViolation();
            valid = false;
        }

        return valid;
    }
}

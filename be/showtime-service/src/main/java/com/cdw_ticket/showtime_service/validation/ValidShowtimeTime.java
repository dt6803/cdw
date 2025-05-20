package com.cdw_ticket.showtime_service.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ShowtimeTimeValidator.class)
public @interface ValidShowtimeTime {
    String message() default "Invalid showtime time";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

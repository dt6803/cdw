package com.cdw_ticket.showtime_service.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {
    SHOWTIME_NOT_FOUND(4004, "Showtime is not found", HttpStatus.NOT_FOUND),
    UNCATEGORIZED_EXCEPTION(1001, "Uncategorized exception", HttpStatus.INTERNAL_SERVER_ERROR),
    ;

    int code;
    String message;
    HttpStatusCode statusCode;
}

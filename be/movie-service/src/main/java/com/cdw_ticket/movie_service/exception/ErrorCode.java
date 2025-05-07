package com.cdw_ticket.movie_service.exception;

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
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    MOVIE_NOT_EXISTED(1001, "Movie not existed", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(403, "Access denied", HttpStatus.UNAUTHORIZED)
    ;

    int code;
    String message;
    HttpStatusCode statusCode;
}

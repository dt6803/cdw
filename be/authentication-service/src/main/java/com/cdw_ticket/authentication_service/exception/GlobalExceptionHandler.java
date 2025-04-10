package com.cdw_ticket.authentication_service.exception;

import com.cdw_ticket.authentication_service.dto.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    ResponseEntity<BaseResponse> handlingRunTimeException(RuntimeException exception) {
        BaseResponse baseResponse = new BaseResponse<>();
        baseResponse.setStatus("Error");
        baseResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());
        baseResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        return ResponseEntity.badRequest().body(baseResponse);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<BaseResponse> handlingAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatus("Error");
        baseResponse.setCode(errorCode.getCode());
        baseResponse.setMessage(errorCode.getMessage());
        return ResponseEntity.status(errorCode.getStatusCode()).body(baseResponse);
    }
}

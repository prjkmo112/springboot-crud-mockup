package com.momo.sparta.mainapi.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionHandlers extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> apiExceptionHandler(ApiException apiException) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(apiException.getStatus())
                .message(apiException.getMessage())
                .build();

        return ResponseEntity.status(apiException.getStatus()).body(errorResponse);
    }

}

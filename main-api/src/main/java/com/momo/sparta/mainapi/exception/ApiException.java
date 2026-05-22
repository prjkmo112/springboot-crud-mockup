package com.momo.sparta.mainapi.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class ApiException extends RuntimeException {

    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int status;
    private final String message;

    public ApiException(String message) {
        super(message);
        this.message = message;
        this.status = HttpStatus.UNPROCESSABLE_CONTENT.value();
    }

    public ApiException(String message, int status) {
        super(message);
        this.message = message;
        this.status = status;
    }

}

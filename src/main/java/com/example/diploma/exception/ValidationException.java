package com.example.diploma.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ValidationException extends RuntimeException {
    private final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationException(String message) {
        super(message);
    }
}
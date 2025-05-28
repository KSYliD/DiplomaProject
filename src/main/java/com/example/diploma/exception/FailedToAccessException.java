package com.example.diploma.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper = false)
public class FailedToAccessException extends RuntimeException {
    private HttpStatus status = HttpStatus.NOT_ACCEPTABLE;

    public FailedToAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public FailedToAccessException(String message) {
        super(message);
    }
}

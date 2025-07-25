package com.example.diploma.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED) // 401
public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException() {
        super("Користувач не авторизований");
    }

    public UnauthorizedException(String message) {
        super(message);
    }
}
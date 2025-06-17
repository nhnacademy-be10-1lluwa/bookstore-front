package com.nhnacademy.illuwa.exception;

public class LoginRequestException extends RuntimeException {
    public LoginRequestException(String message) {
        super(message);
    }
}

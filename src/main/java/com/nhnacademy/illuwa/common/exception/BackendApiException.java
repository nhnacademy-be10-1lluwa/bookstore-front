package com.nhnacademy.illuwa.common.exception;

import lombok.Getter;

@Getter
public class BackendApiException extends RuntimeException {
    private final String errorCode;
    private final int httpStatus;

    public BackendApiException(String message, String errorCode, int httpStatus) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }

}
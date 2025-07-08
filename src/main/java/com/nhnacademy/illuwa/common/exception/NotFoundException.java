package com.nhnacademy.illuwa.common.exception;

import com.nhnacademy.illuwa.common.dto.ErrorResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public class NotFoundException extends BackendApiException{
    private final List<ErrorResponse.ValidationError> validationErrors;

    public NotFoundException(String message, String errorCode, List<ErrorResponse.ValidationError> validationErrors) {
        super(message, errorCode, HttpStatus.BAD_REQUEST.value());
        this.validationErrors = validationErrors;
    }
}

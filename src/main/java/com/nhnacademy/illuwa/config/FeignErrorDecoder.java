package com.nhnacademy.illuwa.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.illuwa.common.dto.ErrorResponse;
import com.nhnacademy.illuwa.common.exception.BackendApiException;
import com.nhnacademy.illuwa.common.exception.ClientBadRequestException;
import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Exception decode(String methodKey, Response response) {
        log.error("Feign request failed: {} with status {}. Reason: {}", methodKey, response.status(), response.reason());

        ErrorResponse errorResponse;
        try (InputStream responseBody = response.body().asInputStream()) {
            errorResponse = objectMapper.readValue(responseBody, ErrorResponse.class);
            log.error("Decoded ErrorResponse from backend: {}", errorResponse);

            HttpStatus httpStatus = HttpStatus.valueOf(response.status());
            String errorMessage = errorResponse.getMessage();
            String errorCode = errorResponse.getCode();

            return switch (httpStatus) {
                case BAD_REQUEST -> new ClientBadRequestException(errorMessage, errorCode, errorResponse.getErrors()); // 400
                default -> new BackendApiException(errorMessage, errorCode, httpStatus.value());
            };

        } catch (IOException e) {
            log.error("ErrorDTO parsing error: {} or I/O error: {}", methodKey, e.getMessage(), e);
            return FeignException.errorStatus(methodKey, response);
        } catch (Exception e) {
            log.error("Unexpected error in FeignErrorDecoder: {}", e.getMessage(), e);
            return FeignException.errorStatus(methodKey, response);
        }
    }
}
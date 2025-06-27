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
            // 1. 응답 본문을 ErrorResponse DTO로 파싱 시도
            errorResponse = objectMapper.readValue(responseBody, ErrorResponse.class);
            log.error("Decoded ErrorResponse from backend: {}", errorResponse);

            // 2. 파싱 성공 시, HTTP 상태 코드와 ErrorResponse의 'code', 'message'를 조합하여 예외 생성
            HttpStatus httpStatus = HttpStatus.valueOf(response.status());
            String errorMessage = errorResponse.getMessage(); // 백엔드에서 보낸 사용자 친화적 메시지
            String errorCode = errorResponse.getCode();       // 백엔드의 비즈니스 에러 코드

            return switch (httpStatus) {
                case BAD_REQUEST -> new ClientBadRequestException(errorMessage, errorCode, errorResponse.getErrors()); // 400
                // 기타 다른 HTTP 상태 코드 처리
                default -> new BackendApiException(errorMessage, errorCode, httpStatus.value());
            };

        } catch (IOException e) {
            // 3. ErrorResponse DTO 파싱 실패 시 (즉, 응답이 ErrorResponse 형식이 아닐 때)
            log.error("Error parsing backend error response body for method {} or I/O error: {}", methodKey, e.getMessage(), e);
            return FeignException.errorStatus(methodKey, response);
        } catch (Exception e) {
            // 파싱 외에 FeignErrorDecoder 자체에서 발생할 수 있는 다른 예외 처리
            log.error("Unexpected error in FeignErrorDecoder: {}", e.getMessage(), e);
            return FeignException.errorStatus(methodKey, response); // <-- 이 부분이 핵심!
        }
    }
}
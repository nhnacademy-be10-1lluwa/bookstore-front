package com.nhnacademy.illuwa.config.handler;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nhnacademy.illuwa.common.dto.ErrorResponse;
import com.nhnacademy.illuwa.common.exception.BackendApiException;
import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.ByteBuffer;

@ControllerAdvice
@Slf4j
public class FrontEndServiceExceptionHandler {
    // FeignErrorDecoder가 변환하여 던진 커스텀 예외들을 처리
    // 예시: BadRequestException, NotFoundException 등 BackendApiException의 하위 클래스들
    @ExceptionHandler(BackendApiException.class)
    public String handleBackendApiException(BackendApiException ex, Model model, HttpServletRequest request) {
        model.addAttribute("error", ex.getMessage());
        model.addAttribute("status", ex.getHttpStatus());
        model.addAttribute("code", ex.getErrorCode());
        model.addAttribute("path", request.getRequestURI());
        model.addAttribute("message", ex.getMessage());

        // 추후 로그인 에러 발생 시 /login 으로 redirect 하는 경우 추가
        // 그 외 여러 status 혹은 code에 따른 경로 추가하기

        return "error";
    }

    @ExceptionHandler(FeignException.class)
    public String handleFeignException(FeignException ex, Model model, HttpServletRequest request) {
        ErrorResponse error = null;
        String jsonToParse = null;

        try {
            String detailMessage = ex.getLocalizedMessage(); // getDetailMessage() 또는 getMessage() 사용 가능
            int jsonStartIndex = detailMessage.indexOf('{');

            if (jsonStartIndex != -1) { // '{' 문자를 찾았다면
                jsonToParse = detailMessage.substring(jsonStartIndex);
                log.error("DetailMessage에서 추출한 JSON 문자열: [{}]", jsonToParse);

                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.registerModule(new JavaTimeModule());
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                error = objectMapper.readValue(jsonToParse, ErrorResponse.class);
                log.error("ErrorResponse 파싱 성공 (from detailMessage): {}", error);
            } else {
                // detailMessage에 JSON이 없거나 파싱할 '{'를 찾지 못한 경우
                log.warn("FeignException의 detailMessage에서 JSON을 찾을 수 없습니다. DetailMessage: [{}]", detailMessage);
                model.addAttribute("error", "서버 응답 파싱 중 오류가 발생했습니다: JSON 형식을 찾을 수 없음");
                model.addAttribute("status", ex.status());
                model.addAttribute("path", request.getRequestURI());
                return "error";
            }
        } catch (Exception e) {
            // 파싱 실패 시 fallback
            model.addAttribute("error", "서버 응답 파싱 중 오류가 발생했습니다.");
            model.addAttribute("status", ex.status());
            model.addAttribute("path", request.getRequestURI());
            return "error";
        }

        if (error != null) {
            model.addAttribute("status", error.getStatus());
            model.addAttribute("code", error.getCode());
            model.addAttribute("path", error.getPath());
            model.addAttribute("message", error.getMessage());
        } else {
            // responseBody가 없거나 파싱 실패 시
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("status", ex.status());
            model.addAttribute("path", request.getRequestURI());
        }

        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception ex, Model model, HttpServletRequest request) {
        model.addAttribute("error", "알 수 없는 오류가 발생했습니다.");
        model.addAttribute("status", 500);
        model.addAttribute("path", request.getRequestURI());
        return "error";
    }
}
package com.nhnacademy.illuwa.config;

import com.nhnacademy.illuwa.common.exception.BackendApiException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class FrontEndServiceExceptionHandler {
    // FeignErrorDecoder가 변환하여 던진 커스텀 예외들을 처리
    // 예시: BadRequestException, NotFoundException 등 BackendApiException의 하위 클래스들
    @ExceptionHandler(BackendApiException.class)
    public String handleBackendApiException(BackendApiException ex, Model model, HttpServletRequest request) {
        model.addAttribute("error", ex.getMessage());
        model.addAttribute("status", ex.getHttpStatus());
        model.addAttribute("code", ex.getErrorCode());
        model.addAttribute("path", request.getRequestURI());

        // 추후 로그인 에러 발생 시 /login 으로 redirect 하는 경우 추가
        // 그 외 여러 status 혹은 code에 따른 경로 추가하기

        return "error";
    }

    @ExceptionHandler(feign.FeignException.class)
    public String handleFeignException(feign.FeignException ex, Model model, HttpServletRequest request) {
        model.addAttribute("error", ex.getMessage());
        model.addAttribute("status", ex.status());
        model.addAttribute("path", request.getRequestURI());
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
package com.nhnacademy.illuwa.config;

import com.nhnacademy.illuwa.exception.ApiRequestException;
import com.nhnacademy.illuwa.exception.LoginRequestException;
import com.nhnacademy.illuwa.exception.SignupRequestException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiRequestException.class)
    public String handleApiError(ApiRequestException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        model.addAttribute("status", 403);
        return "error";
    }

    @ExceptionHandler(LoginRequestException.class)
    public String handleLoginError(LoginRequestException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return "auth/login";
    }

    @ExceptionHandler(SignupRequestException.class)
    public String handleSignupError(SignupRequestException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return "auth/signup";
    }
}

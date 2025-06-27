package com.nhnacademy.illuwa.auth.controller;

import com.nhnacademy.illuwa.auth.dto.MemberRegisterRequest;
import com.nhnacademy.illuwa.auth.service.SignupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class SignupController {

    private final SignupService signupService;

    @GetMapping("/signup")
    public String signup() {
        return "auth/signup";
    }

    @PostMapping("/signup")
    public String signupSubmit(MemberRegisterRequest memberRegisterRequest) {
        signupService.sendSignup(memberRegisterRequest);
        return "redirect:/auth/login";
    }
}

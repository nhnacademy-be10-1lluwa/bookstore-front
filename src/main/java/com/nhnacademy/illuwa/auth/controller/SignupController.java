package com.nhnacademy.illuwa.auth.controller;

import com.nhnacademy.illuwa.auth.client.AuthClient;
import com.nhnacademy.illuwa.auth.dto.MemberRegisterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class SignupController {

    private final AuthClient authClient;

    @GetMapping("/signup")
    public String signup() {
        return "auth/signup";
    }

    @PostMapping("/signup")
    public String signupSubmit(@Valid @ModelAttribute MemberRegisterRequest memberRegisterRequest) {
        authClient.signup(memberRegisterRequest);
        return "redirect:/auth/login";
    }
}

package com.nhnacademy.illuwa.auth.controller;

import com.nhnacademy.illuwa.auth.client.AuthClient;
import com.nhnacademy.illuwa.auth.dto.MemberRegisterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class SignupController {

    private final AuthClient authClient;

    @GetMapping("/signup")
    public String signup(@RequestParam(value = "isPaycoUser", defaultValue = "false") boolean isPaycoUser, Model model) {
        model.addAttribute("isPaycoUser", isPaycoUser);
        return "auth/signup";
    }

    @PostMapping("/signup")
    public String signupSubmit(@Valid @ModelAttribute MemberRegisterRequest memberRegisterRequest) {
        authClient.signup(memberRegisterRequest);
        return "redirect:/login";
    }
}

package com.nhnacademy.illuwa.auth.controller;

import com.nhnacademy.illuwa.auth.client.AuthClient;
import com.nhnacademy.illuwa.auth.dto.MemberLoginRequest;
import com.nhnacademy.illuwa.auth.dto.TokenResponse;
import com.nhnacademy.illuwa.auth.utils.JwtCookieUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.Duration;

@RequiredArgsConstructor
@Controller
public class LoginController {

    private final AuthClient authClient;

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @PostMapping("/login")
    public String loginSubmit(@Valid @ModelAttribute MemberLoginRequest memberLoginRequest,
                              HttpServletResponse response) {
        TokenResponse tokenResponse = authClient.login(memberLoginRequest);

        JwtCookieUtil.addAccessToken(response, tokenResponse.getAccessToken(), (int) tokenResponse.getExpiresIn());
        JwtCookieUtil.addRefreshToken(response, tokenResponse.getRefreshToken(), (int) Duration.ofDays(14).toSeconds());

        return "redirect:/";
    }
}

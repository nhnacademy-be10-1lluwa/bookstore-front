package com.nhnacademy.illuwa.auth.controller;

import com.nhnacademy.illuwa.auth.client.AuthClient;
import com.nhnacademy.illuwa.auth.dto.MemberLoginRequest;
import com.nhnacademy.illuwa.auth.dto.TokenResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
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

        addCookie(response, "ACCESS_TOKEN", tokenResponse.getAccessToken(), (int) tokenResponse.getExpiresInSeconds());
        addCookie(response, "REFRESH_TOKEN", tokenResponse.getRefreshToken(), (int) Duration.ofDays(14).toSeconds());

        return "redirect:/";
    }

    private void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        ResponseCookie c = ResponseCookie.from(name, value)
                .httpOnly(true)
                .secure(true)
                .sameSite("Lax")
                .path("/")
                .maxAge(maxAge)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, c.toString());
    }
}

package com.nhnacademy.illuwa.auth.controller;

import com.nhnacademy.illuwa.auth.client.AuthClient;
import com.nhnacademy.illuwa.auth.dto.MemberLoginResponse;
import com.nhnacademy.illuwa.auth.dto.MemberLoginRequest;
import com.nhnacademy.illuwa.auth.dto.TokenRefreshRequest;
import com.nhnacademy.illuwa.auth.dto.TokenResponse;
import com.nhnacademy.illuwa.auth.utils.JwtCookieUtil;
import com.nhnacademy.illuwa.member.enums.Status;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class LoginController {

    private final AuthClient authClient;

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @PostMapping("/login")
    public String loginSubmit(@Valid @ModelAttribute MemberLoginRequest request,
                              HttpServletResponse response) {

        MemberLoginResponse loginResponse = authClient.login(request);

        if(loginResponse.getStatus().equals(Status.INACTIVE)){
            return "auth/inactive-verification";
        }

        JwtCookieUtil.addAccessToken(response, loginResponse.getAccessToken(), (int) loginResponse.getExpiresIn());
        JwtCookieUtil.addRefreshToken(response, loginResponse.getRefreshToken(), (int) Duration.ofDays(14).toSeconds());

        return "redirect:/";
    }

    @PostMapping("/refresh")
    public String refreshToken(@CookieValue(value = "ACCESS_TOKEN", required = false) String accessToken,
                               @CookieValue(value = "REFRESH_TOKEN", required = false) String refreshToken,
                               HttpServletResponse response) {
        TokenRefreshRequest tokenRefreshRequest = new TokenRefreshRequest(refreshToken, accessToken);
        TokenResponse newTokenResponse = authClient.refreshToken(tokenRefreshRequest);

        JwtCookieUtil.addAccessToken(response, newTokenResponse.getAccessToken(), (int) newTokenResponse.getExpiresIn());
        JwtCookieUtil.addRefreshToken(response, newTokenResponse.getRefreshToken(), (int) Duration.ofDays(14).toSeconds());

        return "redirect:/";
    }
}

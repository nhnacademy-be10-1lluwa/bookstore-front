package com.nhnacademy.illuwa.auth.controller;

import com.nhnacademy.illuwa.auth.client.AuthClient;
import com.nhnacademy.illuwa.auth.dto.MemberLoginResponse;
import com.nhnacademy.illuwa.auth.dto.MemberLoginRequest;
import com.nhnacademy.illuwa.auth.utils.JwtCookieUtil;
import com.nhnacademy.illuwa.member.enums.Status;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}

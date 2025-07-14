package com.nhnacademy.illuwa.auth.controller;

import com.nhnacademy.illuwa.auth.client.AuthClient;
import com.nhnacademy.illuwa.auth.dto.InactiveCheckResponse;
import com.nhnacademy.illuwa.auth.dto.MemberLoginRequest;
import com.nhnacademy.illuwa.auth.dto.SendVerificationRequest;
import com.nhnacademy.illuwa.auth.dto.TokenResponse;
import com.nhnacademy.illuwa.auth.service.InactiveVerificationService;
import com.nhnacademy.illuwa.auth.utils.JwtCookieUtil;
import com.nhnacademy.illuwa.member.enums.Status;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.Duration;

@RequiredArgsConstructor
@Controller
public class LoginController {

    private final AuthClient authClient;
    private final InactiveVerificationService inactiveVerificationService;


    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @PostMapping("/login")
    public String loginSubmit(@Valid @ModelAttribute MemberLoginRequest request,
                              HttpServletResponse response,
                              Model model) {

        InactiveCheckResponse inactiveMemberInfo = inactiveVerificationService.getInactiveMemberInfo(new SendVerificationRequest(request.getEmail()));

        if (inactiveMemberInfo.getStatus() == Status.INACTIVE) {
            model.addAttribute("email", inactiveMemberInfo.getEmail());
            return "auth/inactive-verification";
        }

        TokenResponse tokenResponse = authClient.login(request);

        JwtCookieUtil.addAccessToken(response, tokenResponse.getAccessToken(), (int) tokenResponse.getExpiresIn());
        JwtCookieUtil.addRefreshToken(response, tokenResponse.getRefreshToken(), (int) Duration.ofDays(14).toSeconds());

        return "redirect:/";
    }
}

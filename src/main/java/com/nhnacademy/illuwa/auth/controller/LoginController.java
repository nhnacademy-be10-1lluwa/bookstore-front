package com.nhnacademy.illuwa.auth.controller;

import com.nhnacademy.illuwa.auth.client.AuthClient;
import com.nhnacademy.illuwa.auth.dto.MemberLoginRequest;
import com.nhnacademy.illuwa.auth.dto.TokenResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class LoginController {

    private final AuthClient authClient;


    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @PostMapping("/login")
    public String loginSubmit(@ModelAttribute MemberLoginRequest memberLoginRequest,
                              HttpServletResponse response) {
//        TokenResponse tokenResponse = authClient.login(memberLoginRequest);
//        String token = tokenResponse.getAccessToken();
//
//        Cookie accessTokenCookie = new Cookie("ACCESS_TOKEN", token);
//        accessTokenCookie.setHttpOnly(true);
//        accessTokenCookie.setSecure(true);
//        accessTokenCookie.setPath("/");
//        accessTokenCookie.setMaxAge(60 * 60); // 1시간
//
//        response.addCookie(accessTokenCookie);
        return "redirect:/";
    }
}

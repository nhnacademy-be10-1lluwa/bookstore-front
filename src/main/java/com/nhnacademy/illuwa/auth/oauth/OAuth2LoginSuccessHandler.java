package com.nhnacademy.illuwa.auth.oauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.illuwa.auth.client.AuthClient;
import com.nhnacademy.illuwa.auth.dto.SocialLoginRequest;
import com.nhnacademy.illuwa.auth.dto.TokenResponse;
import com.nhnacademy.illuwa.auth.utils.JwtCookieUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final AuthClient authClient;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String provider = "payco";
        String providerId = oAuth2User.getName();

        TokenResponse tokenResponse = authClient.socialLogin(new SocialLoginRequest(provider, providerId, oAuth2User.getAttributes()));

        JwtCookieUtil.addAccessToken(response, tokenResponse.getAccessToken(), (int) tokenResponse.getExpiresIn());
        JwtCookieUtil.addRefreshToken(response, tokenResponse.getRefreshToken(), (int) Duration.ofDays(14).toSeconds());

        response.sendRedirect("/");
    }
}

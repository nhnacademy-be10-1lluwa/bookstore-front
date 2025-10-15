package com.nhnacademy.illuwa.config.handler;

import com.nhnacademy.illuwa.auth.client.AuthClient;
import com.nhnacademy.illuwa.auth.dto.TokenResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

    private final AuthClient authClient;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        String accessToken = extractTokenFromCookie(request, "ACCESS_TOKEN");
        String refreshToken = extractTokenFromCookie(request, "REFRESH_TOKEN");
        TokenResponse tokenResponse = new TokenResponse(accessToken, refreshToken, 1);

        authClient.logout(tokenResponse);
    }

    private String extractTokenFromCookie(HttpServletRequest request, String cookieName) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}

package com.nhnacademy.illuwa.auth.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;

import java.util.Objects;

public class JwtCookieUtil {
    public static void addAccessToken(HttpServletResponse response, String value, int maxAge) {
        ResponseCookie c = ResponseCookie.from("ACCESS_TOKEN", value)
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .path("/")
                .maxAge(maxAge)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, c.toString());
    }

    public static void addRefreshToken(HttpServletResponse response, String value, int maxAge) {
        ResponseCookie c = ResponseCookie.from("REFRESH_TOKEN", value)
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .path("/")
                .maxAge(maxAge)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, c.toString());
    }

    public static boolean checkAccessToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(Objects.isNull(cookies)) {
            return false;
        }

        for(Cookie cookie : cookies) {
            if("ACCESS_TOKEN".equals(cookie.getName())) {
                return true;
            }
        }
        return false;
    }
}

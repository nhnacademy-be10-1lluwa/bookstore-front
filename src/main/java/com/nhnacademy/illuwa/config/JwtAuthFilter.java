package com.nhnacademy.illuwa.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

public class JwtAuthFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 이미 인증된 상태면 스킵
        if(SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }

        // ACCESS_TOKEN 쿠키 조회
        Cookie cookie = WebUtils.getCookie(request, "ACCESS_TOKEN");
        if(cookie == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // 디코딩 실패 -> 비인증 처리
        JwtPayload payload = decodePayload(cookie.getValue());
        if(payload == null) {
            filterChain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(
                        payload,
                        null,
                        List.of(new SimpleGrantedAuthority("ROLE_" + payload.getRole()))
                );

        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request, response);
    }

    // JWT payload 부분만 디코딩
    private JwtPayload decodePayload(String jwt) {
        try {
            String[] parts = jwt.split("\\.");
            if(parts.length < 2) {
                return null;
            }
            String json = new String(Base64.getDecoder().decode(parts[1]), StandardCharsets.UTF_8);
            return objectMapper.readValue(json, JwtPayload.class);
        } catch (Exception e) {
            return null;
        }
    }

    // 토큰 payload 모델
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class JwtPayload {
        private Long userId;
        private String role;
    }
}

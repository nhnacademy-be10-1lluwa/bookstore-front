package com.nhnacademy.illuwa.config;

import feign.RequestInterceptor;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

import java.util.List;

@Configuration
public class FeignAuthConfig {
    private static final String CORRELATION_ID_HEADER = "X-Correlation-ID";
    private static final String CORRELATION_ID_LOG_KEY = "correlationId";

    // 모든 Feign 요청에 Authorization 헤더 주입
    @Bean
    public RequestInterceptor feignAuthInterceptor() {
        return template -> {
            // 화이트 리스트 경로
            List<String> whiteList = List.of("/books/bestseller");
            String url = template.path();

            boolean isWhiteListed = whiteList.stream().anyMatch(url::startsWith);
            if (isWhiteListed) {
                return;
            }

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attr == null) {
                return;
            }

            HttpServletRequest request = attr.getRequest();
            // 1. Authorization 헤더 주입
            Cookie cookie = WebUtils.getCookie(request, "ACCESS_TOKEN");
            if (cookie != null) {
                template.header("Authorization", "Bearer " + cookie.getValue());
            }

            // ✅ 2. Correlation ID 헤더 주입
            String correlationId = request.getHeader(CORRELATION_ID_HEADER);
            if (correlationId == null) {
                // 혹시 누락된 경우 MDC에서 가져오거나 새로 생성
                correlationId = MDC.get(CORRELATION_ID_LOG_KEY);
                if (correlationId == null) {
                    correlationId = java.util.UUID.randomUUID().toString();
                }
            }
            template.header(CORRELATION_ID_HEADER, correlationId);
        };
    }
}

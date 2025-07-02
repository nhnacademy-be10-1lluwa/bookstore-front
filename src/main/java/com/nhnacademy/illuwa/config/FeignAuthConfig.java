package com.nhnacademy.illuwa.config;

import feign.RequestInterceptor;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

@Configuration
public class FeignAuthConfig {

    // 모든 Feign 요청에 Authorization 헤더 주입
    @Bean
    public RequestInterceptor feignAuthInterceptor() {
        return template -> {
            ServletRequestAttributes attr =
                    (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attr == null) {
                return;
            }

            HttpServletRequest request = attr.getRequest();
            Cookie cookie = WebUtils.getCookie(request, "ACCESS_TOKEN");
            if (cookie != null) {
                template.header("Authorization", "Bearer " + cookie.getValue());
            }
        };
    }
}

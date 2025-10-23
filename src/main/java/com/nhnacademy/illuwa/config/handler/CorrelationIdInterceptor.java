package com.nhnacademy.illuwa.config.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Component
public class CorrelationIdInterceptor implements HandlerInterceptor {
    private static final String CORRELATION_ID_HEADER = "X-Correlation-ID";
    private static final String CORRELATION_ID_LOG_KEY = "correlationId";
    private static final Logger log = LoggerFactory.getLogger(CorrelationIdInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 클라이언트에서 들어온 CID가 있으면 유지, 없으면 새로 생성
        String correlationId = request.getHeader(CORRELATION_ID_HEADER);
        if (correlationId == null || correlationId.isBlank()) {
            correlationId = UUID.randomUUID().toString();
            log.info("📘 새로운 Correlation-ID 생성: {}", correlationId);
        }

        // MDC 및 Response 헤더에 저장
        MDC.put(CORRELATION_ID_LOG_KEY, correlationId);
        response.setHeader(CORRELATION_ID_HEADER, correlationId);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        MDC.clear();
    }
}

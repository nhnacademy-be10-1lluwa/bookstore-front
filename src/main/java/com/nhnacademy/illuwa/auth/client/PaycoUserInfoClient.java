package com.nhnacademy.illuwa.auth.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class PaycoUserInfoClient {

    @Value("${spring.security.oauth2.client.registration.payco.client-id}")
    private String clientId;

    private final RestTemplate restTemplate;

    public Map<String, Object> getUserInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("client_id", clientId);
        headers.set("access_token", accessToken);

        HttpEntity<String> entity = new HttpEntity<>("{}", headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                "https://apis-payco.krp.toastoven.net/payco/friends/find_member_v2.json",
                HttpMethod.POST,
                entity,
                Map.class
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            Map<String, Object> body = response.getBody();
            if (body == null || !((Map<String, Object>) body.get("header")).get("isSuccessful").equals(true)) {
                throw new RuntimeException("회원 정보 조회 실패: " + body);
            }
            return (Map<String, Object>) ((Map<String, Object>) body.get("data")).get("member");
        } else {
            throw new RuntimeException("PAYCO 회원 정보 요청 실패: " + response.getStatusCode());
        }
    }
}

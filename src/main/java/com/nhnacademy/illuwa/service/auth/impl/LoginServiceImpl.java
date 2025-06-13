package com.nhnacademy.illuwa.service.auth.impl;

import com.nhnacademy.illuwa.dto.auth.MemberLoginRequest;
import com.nhnacademy.illuwa.dto.auth.MemberLoginResponse;
import com.nhnacademy.illuwa.exception.ApiRequestException;
import com.nhnacademy.illuwa.exception.LoginRequestException;
import com.nhnacademy.illuwa.service.auth.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class LoginServiceImpl implements LoginService {
    private final RestTemplate restTemplate;

    public void sendLogin(MemberLoginRequest memberLoginRequest) {
        String apiUrl = "http://api서버주소/api/login";

        try {
            ResponseEntity<MemberLoginResponse> response = restTemplate.postForEntity(apiUrl, memberLoginRequest, MemberLoginResponse.class);

            if (!(response.getStatusCode() == HttpStatus.OK && response.getBody().isSuccess())) {
                throw new LoginRequestException("로그인 정보가 올바르지 않습니다.");
            }
        } catch (RestClientException e) {
            throw new ApiRequestException("서버와 통신 중 장애가 발생했습니다.");
        }
    }
}

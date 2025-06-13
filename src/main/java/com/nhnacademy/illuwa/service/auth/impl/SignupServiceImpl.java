package com.nhnacademy.illuwa.service.auth.impl;

import com.nhnacademy.illuwa.dto.member.MemberRegisterRequest;
import com.nhnacademy.illuwa.dto.member.MemberRegisterResponse;
import com.nhnacademy.illuwa.exception.ApiRequestException;
import com.nhnacademy.illuwa.exception.SignupRequestException;
import com.nhnacademy.illuwa.service.auth.SignupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class SignupServiceImpl implements SignupService {
    private final RestTemplate restTemplate;

    public void sendSignup(MemberRegisterRequest memberRegisterRequest) {
        String apiUrl = "http://api서버주소/api/login";

        try {
            ResponseEntity<MemberRegisterResponse> response = restTemplate.postForEntity(apiUrl, memberRegisterRequest, MemberRegisterResponse.class);

            if (!(response.getStatusCode() == HttpStatus.OK && response.getBody().isSuccess())) {
                throw new SignupRequestException("회원 정보가 올바르지 않습니다.");
            }
        } catch (RestClientException e) {
            throw new ApiRequestException("서버와 통신 중 장애가 발생했습니다.");
        }
    }
}

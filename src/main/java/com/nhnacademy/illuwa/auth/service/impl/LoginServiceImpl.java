package com.nhnacademy.illuwa.auth.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.illuwa.common.dto.ErrorResponse;
import com.nhnacademy.illuwa.common.exception.ApiRequestException;
import com.nhnacademy.illuwa.common.exception.LoginRequestException;
import com.nhnacademy.illuwa.auth.dto.MemberResponse;
import com.nhnacademy.illuwa.auth.dto.MemberLoginRequest;
import com.nhnacademy.illuwa.auth.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class LoginServiceImpl implements LoginService {

    @Value("${api.base-url}")
    String apiUrl;

    private final RestTemplate restTemplate;
    ObjectMapper objectMapper = new ObjectMapper();

    public MemberResponse sendLogin(MemberLoginRequest memberLoginRequest) {
        ResponseEntity<MemberResponse> response;
        String url = apiUrl + "/login";

        try {
            response = restTemplate.postForEntity(url, memberLoginRequest, MemberResponse.class);

        } catch (HttpClientErrorException e) { // 4xx 클라이언트 에러
            try {
                ErrorResponse errorBody = objectMapper.readValue(e.getResponseBodyAsString(), ErrorResponse.class);

                // 파싱된 `code` 값을 기반으로 로직 분기
                if ("USER_NOT_FOUND".equals(errorBody.getCode())) {
                    throw new LoginRequestException(errorBody.getMessage());
                } else {
                    throw new ApiRequestException("로그인 요청 중 오류 발생: " + errorBody.getMessage());
                }
            } catch (Exception parseException) {
                System.err.println("백엔드 오류 응답 본문 파싱 실패: " + parseException.getMessage());
                throw new ApiRequestException("서버 응답 형식이 올바르지 않습니다.");
            }
        } catch (RestClientException e) { // 그 외 RestClientException (네트워크 오류, 5xx 서버 오류)
            if (e instanceof HttpServerErrorException serverError) {
                try {
                    ErrorResponse errorBody = objectMapper.readValue(serverError.getResponseBodyAsString(), ErrorResponse.class);
                    throw new ApiRequestException("백엔드 서버 내부 오류: " + errorBody.getMessage());
                } catch (Exception parseException) {
                    System.err.println("서버 오류 응답 본문 파싱 실패: " + parseException.getMessage());
                    throw new ApiRequestException("서버와 통신 중 예상치 못한 오류가 발생했습니다. (5xx)");
                }
            } else {
                throw new ApiRequestException("서버와 연결할 수 없습니다. 네트워크 상태를 확인해주세요.");
            }
        }
        return response.getBody();
    }
}

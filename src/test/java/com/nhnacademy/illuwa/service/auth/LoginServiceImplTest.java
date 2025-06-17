package com.nhnacademy.illuwa.service.auth;

import com.nhnacademy.illuwa.dto.auth.MemberLoginRequest;
import com.nhnacademy.illuwa.dto.member.MemberResponse;
import com.nhnacademy.illuwa.exception.ApiRequestException;
import com.nhnacademy.illuwa.exception.LoginRequestException;
import com.nhnacademy.illuwa.service.auth.impl.LoginServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static com.nhnacademy.illuwa.enums.Role.USER;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    private LoginService loginService;

    @BeforeEach
    void setUp() {
        loginService = new LoginServiceImpl(restTemplate);
    }

    @Test
    void Login_success() {
        MemberLoginRequest request = new MemberLoginRequest("test@example.com", "password");
        MemberResponse fakeResponse = new MemberResponse();

        ResponseEntity<MemberResponse> responseEntity =
                new ResponseEntity<>(fakeResponse, HttpStatus.OK);

        when(restTemplate.postForEntity(
                anyString(),
                eq(request),
                eq(MemberResponse.class))
        ).thenReturn(responseEntity);

        assertDoesNotThrow(() -> loginService.sendLogin(request));
    }

    @Test
    void Login_fail_RequestException() {
        MemberLoginRequest request = new MemberLoginRequest("test", "password");
        MemberResponse fakeResponse = new MemberResponse();

        ResponseEntity<MemberResponse> responseEntity =
                new ResponseEntity<>(fakeResponse, HttpStatus.OK);

        when(restTemplate.postForEntity(
                anyString(),
                eq(request),
                eq(MemberResponse.class))
        ).thenReturn(responseEntity);

        assertThrows(LoginRequestException.class, () -> loginService.sendLogin(request));
    }

    @Test
    void Login_fail_ResponseException() {
        MemberLoginRequest request = new MemberLoginRequest("test", "password");
        MemberResponse fakeResponse = new MemberResponse();

        ResponseEntity<MemberResponse> responseEntity =
                new ResponseEntity<>(fakeResponse, HttpStatus.BAD_REQUEST);

        when(restTemplate.postForEntity(
                anyString(),
                eq(request),
                eq(MemberResponse.class))
        ).thenReturn(responseEntity);

        assertThrows(LoginRequestException.class, () -> loginService.sendLogin(request));
    }

    @Test
    void Login_fail_RestTemplateException() {
        MemberLoginRequest request = new MemberLoginRequest("test@example.com", "password");

        when(restTemplate.postForEntity(
                anyString(),
                eq(request),
                eq(MemberResponse.class))
        ).thenThrow(new RestClientException("서버 연결 실패"));

        assertThrows(ApiRequestException.class, () -> loginService.sendLogin(request));
    }
}

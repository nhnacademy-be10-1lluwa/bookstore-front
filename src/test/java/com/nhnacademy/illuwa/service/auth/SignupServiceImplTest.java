package com.nhnacademy.illuwa.service.auth;

import com.nhnacademy.illuwa.dto.member.MemberRegisterRequest;
import com.nhnacademy.illuwa.dto.member.MemberRegisterResponse;
import com.nhnacademy.illuwa.exception.ApiRequestException;
import com.nhnacademy.illuwa.exception.SignupRequestException;
import com.nhnacademy.illuwa.service.auth.impl.SignupServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SignupServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    private SignupService signupService;

    @BeforeEach
    public void setUp() {
        signupService = new SignupServiceImpl(restTemplate);
    }

    @Test
    public void signup_success() {
        MemberRegisterRequest request = new MemberRegisterRequest("test", "1111-11-11", "email@example.com", "test", "010-1111-1111");
        MemberRegisterResponse fakeResponse = new MemberRegisterResponse(true);

        ResponseEntity<MemberRegisterResponse> responseEntity =
                new ResponseEntity<>(fakeResponse, HttpStatus.OK);

        when(restTemplate.postForEntity(
                anyString(),
                eq(request),
                eq(MemberRegisterResponse.class))
        ).thenReturn(responseEntity);

        assertDoesNotThrow(() -> signupService.sendSignup(request));
    }

    @Test
    void Signup_fail_RequestException() {
        MemberRegisterRequest request = new MemberRegisterRequest("test", "1111-11-11", "email@example.com", "test", "010-1111-1111");
        MemberRegisterResponse fakeResponse = new MemberRegisterResponse(false);

        ResponseEntity<MemberRegisterResponse> responseEntity =
                new ResponseEntity<>(fakeResponse, HttpStatus.OK);

        when(restTemplate.postForEntity(
                anyString(),
                eq(request),
                eq(MemberRegisterResponse.class))
        ).thenReturn(responseEntity);

        assertThrows(SignupRequestException.class, () -> signupService.sendSignup(request));
    }

    @Test
    void Signup_fail_ResponseException() {
        MemberRegisterRequest request = new MemberRegisterRequest("test", "1111-11-11", "email@example.com", "test", "010-1111-1111");
        MemberRegisterResponse fakeResponse = new MemberRegisterResponse(false);

        ResponseEntity<MemberRegisterResponse> responseEntity =
                new ResponseEntity<>(fakeResponse, HttpStatus.BAD_REQUEST);

        when(restTemplate.postForEntity(
                anyString(),
                eq(request),
                eq(MemberRegisterResponse.class))
        ).thenReturn(responseEntity);

        assertThrows(SignupRequestException.class, () -> signupService.sendSignup(request));
    }

    @Test
    void Signup_fail_RestTemplateException() {
        MemberRegisterRequest request = new MemberRegisterRequest("test", "1111-11-11", "email@example.com", "test", "010-1111-1111");

        when(restTemplate.postForEntity(
                anyString(),
                eq(request),
                eq(MemberRegisterResponse.class))
        ).thenThrow(new RestClientException("서버 연결 실패"));

        assertThrows(ApiRequestException.class, () -> signupService.sendSignup(request));
    }
}

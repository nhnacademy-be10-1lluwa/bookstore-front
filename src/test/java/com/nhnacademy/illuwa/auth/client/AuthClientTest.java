package com.nhnacademy.illuwa.auth.client;

import com.nhnacademy.illuwa.auth.dto.MemberLoginRequest;
import com.nhnacademy.illuwa.auth.dto.MemberRegisterRequest;
import com.nhnacademy.illuwa.auth.dto.TokenResponse;
import com.nhnacademy.illuwa.auth.dto.payco.SocialLoginRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthClientTest {

    @Mock
    private AuthClient authClient;

    private MemberRegisterRequest registerRequest;
    private MemberLoginRequest loginRequest;
    private SocialLoginRequest socialLoginRequest;
    private TokenResponse tokenResponse;

    @BeforeEach
    public void setUp() {
        registerRequest = new MemberRegisterRequest(
                "John Doe",
                null,
                "john@example.com",
                "Password1!",
                "010-1234-5678"
        );
        loginRequest = new MemberLoginRequest("john@example.com", "Password1!");
        socialLoginRequest = new SocialLoginRequest("google", "google123", null);
        tokenResponse = new TokenResponse("access-token", "refresh-token", 100000);
    }

    @Test
    @DisplayName("회원가입 작동 테스트")
    public void testSignup() {
        doNothing().when(authClient).signup(any(MemberRegisterRequest.class));
        authClient.signup(registerRequest);
        verify(authClient, times(1)).signup(registerRequest);
    }

    @Test
    @DisplayName("로그인 작동 테스트")
    public void testLogin() {
        when(authClient.login(any(MemberLoginRequest.class))).thenReturn(tokenResponse);
        TokenResponse response = authClient.login(loginRequest);

        assertNotNull(response);
        assertEquals("access-token", response.getAccessToken());
        assertEquals("refresh-token", response.getRefreshToken());
        verify(authClient, times(1)).login(loginRequest);
    }

    @Test
    public void testSocialLogin() {
        when(authClient.socialLogin(any(SocialLoginRequest.class))).thenReturn(tokenResponse);
        TokenResponse response = authClient.socialLogin(socialLoginRequest);

        assertNotNull(response);
        assertEquals("access-token", response.getAccessToken());
        assertEquals("refresh-token", response.getRefreshToken());
        verify(authClient, times(1)).socialLogin(socialLoginRequest);
    }

}

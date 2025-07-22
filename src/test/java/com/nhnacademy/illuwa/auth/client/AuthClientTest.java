package com.nhnacademy.illuwa.auth.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.nhnacademy.illuwa.auth.dto.MemberLoginRequest;
import com.nhnacademy.illuwa.auth.dto.MemberLoginResponse;
import com.nhnacademy.illuwa.auth.dto.MemberRegisterRequest;
import com.nhnacademy.illuwa.auth.dto.TokenResponse;
import com.nhnacademy.illuwa.auth.dto.payco.SocialLoginRequest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.nhnacademy.illuwa.member.enums.Status.ACTIVE;

@SpringBootTest(
        properties = {
        "api.base-url=http://localhost:9876"
        },
        webEnvironment = SpringBootTest.WebEnvironment.NONE
)
public class AuthClientTest {
    static WireMockServer wireMockServer;
    static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule()).configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);


    @Autowired
    AuthClient client;

    @MockBean
    ClientRegistrationRepository clientRegistrationRepository;

    @BeforeAll
    static void startWireMock() {
        wireMockServer = new WireMockServer(9876);
        wireMockServer.start();
    }
    @AfterAll
    static void stopWireMock() {
        if (wireMockServer != null) wireMockServer.stop();
    }
    @BeforeEach
    void resetMocks() {
        wireMockServer.resetAll();
    }

    @Test
    @DisplayName("회원가입 동작 확인")
    void testSignup() throws Exception {
        MemberRegisterRequest memberRegisterRequest = new MemberRegisterRequest("riveroad", LocalDate.of(2000, 8, 25), "riveroad@kakao.com", "testPassword1234!", "010-1111-1111");

        wireMockServer.stubFor(WireMock.post(WireMock.urlPathEqualTo("/api/auth/signup"))
                .withRequestBody(equalToJson(objectMapper.writeValueAsString(memberRegisterRequest)))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(204)));

        client.signup(memberRegisterRequest);
    }

    @Test
    @DisplayName("로그인 동작 확인")
    void testLogin() throws Exception {
        MemberLoginRequest memberLoginRequest = new MemberLoginRequest("riveroad@kakao.com", "testPassword1234!");
        MemberLoginResponse loginResponse = new MemberLoginResponse("access_token", "refresh_token", 10000, ACTIVE);

        wireMockServer.stubFor(WireMock.post(WireMock.urlPathEqualTo("/api/auth/login"))
                .withRequestBody(equalToJson(objectMapper.writeValueAsString(memberLoginRequest)))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(loginResponse))
                        .withStatus(200))
        );

        MemberLoginResponse result = client.login(memberLoginRequest);
        Assertions.assertEquals(loginResponse, result);
    }

    @Test
    @DisplayName("소셜 로그인 동작 확인")
    void testSocialLogin() throws Exception {
        SocialLoginRequest socialLoginRequest = new SocialLoginRequest(
                "kakao",
                "kakao-12345678",
                new HashMap<>(Map.of(
                        "nickname", "riveroad",
                        "email", "riveroad@kakao.com",
                        "profileImage", "profile.png"
                ))
        );
        TokenResponse tokenResponse = new TokenResponse("access_token", "refresh_token", 10000);

        wireMockServer.stubFor(WireMock.post(WireMock.urlPathEqualTo("/api/auth/social-login"))
                .withRequestBody(equalToJson(objectMapper.writeValueAsString(socialLoginRequest)))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(tokenResponse))
                        .withStatus(200))
        );

        TokenResponse result = client.socialLogin(socialLoginRequest);
        Assertions.assertEquals(tokenResponse, result);
    }

}

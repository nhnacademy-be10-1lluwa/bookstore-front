package com.nhnacademy.illuwa.auth.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.nhnacademy.illuwa.auth.dto.message.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.test.context.ActiveProfiles;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(
        properties = {
                "api.base-url=http://localhost:9876"
        },
        webEnvironment = SpringBootTest.WebEnvironment.NONE
)
class InactiveVerificationServiceClientIntegrationTest {

    static WireMockServer wireMockServer;
    static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    InactiveVerificationServiceClient client;

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
    @DisplayName("회원 휴면상태 체크")
    void getInactiveMemberInfo() throws Exception {
        SendVerificationRequest request = new SendVerificationRequest("inactive@test.com");
        InactiveCheckResponse response = InactiveCheckResponse.builder()
                .status(com.nhnacademy.illuwa.member.enums.Status.INACTIVE)
                .name("inactive")
                .email("inactive@test.com")
                .contact("010-1111-1111")
                .build();

        wireMockServer.stubFor(post(urlEqualTo("/api/members/check-status"))
                .withRequestBody(equalToJson(objectMapper.writeValueAsString(request)))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(response))
                        .withStatus(200)));

        InactiveCheckResponse actual = client.getInactiveMemberInfo(request);
        assertEquals(response, actual);
    }

    @Test
    @DisplayName("인증코드 전송")
    void sendCode() throws Exception {
        SendVerificationRequest req = new SendVerificationRequest("user@test.com");
        SendMessageResponse res = new SendMessageResponse(true, "user@test.com", "ok", "message");

        wireMockServer.stubFor(post(urlEqualTo("/api/members/inactive/verification"))
                .withRequestBody(equalToJson(objectMapper.writeValueAsString(req)))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(res))
                        .withStatus(200)));

        SendMessageResponse actual = client.sendCode(req);
        assertEquals(res, actual);
    }

    @Test
    @DisplayName("인증코드 검증")
    void verify() throws Exception {
        VerifyCodeRequest req = new VerifyCodeRequest("user@test.com", "code123");
        VerifyCodeResponse res = new VerifyCodeResponse(true, 99L, "user@test.com", "success");

        wireMockServer.stubFor(post(urlEqualTo("/api/members/inactive/verification/verify"))
                .withRequestBody(equalToJson(objectMapper.writeValueAsString(req)))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(res))
                        .withStatus(200)));

        VerifyCodeResponse actual = client.verify(req);
        assertEquals(res, actual);
    }
}
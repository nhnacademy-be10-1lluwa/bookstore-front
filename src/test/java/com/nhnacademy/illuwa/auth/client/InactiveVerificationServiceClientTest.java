package com.nhnacademy.illuwa.auth.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.nhnacademy.illuwa.auth.dto.message.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(
        properties = {
                "api.base-url=http://localhost:9876"
        },
        webEnvironment = SpringBootTest.WebEnvironment.NONE
)
public class InactiveVerificationServiceClientTest {

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
    @DisplayName("인증코드 전송")
    void sendCode() throws Exception {
        SendVerificationRequest req = new SendVerificationRequest("010-1234-5678");

        wireMockServer.stubFor(post(urlEqualTo("/api/members/inactive-verifications"))
                .withRequestBody(equalToJson(objectMapper.writeValueAsString(req)))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(true))
                        .withStatus(200)));

        assertEquals(true, client.sendCode(req));
    }

    @Test
    @DisplayName("인증코드 검증")
    void verify() throws Exception {
        VerifyCodeRequest req = new VerifyCodeRequest("010-1234-5678", "code123");

        wireMockServer.stubFor(post(urlEqualTo("/api/members/inactive-verifications/verify"))
                .withRequestBody(equalToJson(objectMapper.writeValueAsString(req)))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(true))
                        .withStatus(200)));

        assertEquals(true, client.verify(req));
    }
}
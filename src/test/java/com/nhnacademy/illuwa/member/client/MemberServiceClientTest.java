package com.nhnacademy.illuwa.member.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.nhnacademy.illuwa.member.dto.MemberResponse;
import com.nhnacademy.illuwa.member.dto.MemberUpdateRequest;
import com.nhnacademy.illuwa.member.enums.Role;
import com.nhnacademy.illuwa.member.enums.Status;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.test.context.TestPropertySource;
import com.github.tomakehurst.wiremock.client.WireMock;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@EnableFeignClients
@TestPropertySource(properties = {
        "spring.security.enabled=false", // Security 비활성화
        "api.base-url=http://localhost:8089"
})
class MemberServiceClientTest {

    private static WireMockServer wireMockServer;

    @Autowired
    private MemberServiceClient memberServiceClient;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void setup() {
        wireMockServer = new WireMockServer(options().port(8089));
        wireMockServer.start();
    }

    @AfterAll
    static void tearDown() {
        wireMockServer.stop();
    }

    @BeforeEach
    void resetWireMock() {
        wireMockServer.resetAll();
    }

    @Test
    void testGetMember() throws Exception {
        // Mock 데이터 준비
        MemberResponse mockResponse = MemberResponse.builder()
                .memberId(1L)
                .paycoId("payco123")
                .name("홍길동")
                .birth(LocalDate.of(1990, 1, 1))
                .email("hong@example.com")
                .role(Role.USER)
                .contact("010-1234-5678")
                .gradeName("GOLD")
                .point(new BigDecimal("1000.00"))
                .status(Status.ACTIVE)
                .createdAt(LocalDateTime.now())
                .lastLoginAt(LocalDateTime.now())
                .build();

        // WireMock으로 API 응답 모킹 (X-USER-ID 헤더 포함)
        wireMockServer.stubFor(WireMock.get("/api/members")
                .withHeader("X-USER-ID", WireMock.equalTo("test-user")) // Security 헤더 모킹
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withHeader("Access-Control-Allow-Origin", "*") // CORS 헤더 모킹
                        .withBody(objectMapper.writeValueAsString(mockResponse))));

        // 테스트 실행
        MemberResponse response = memberServiceClient.getMember();

        // 검증
        assertNotNull(response);
        assertEquals(1L, response.getMemberId());
        assertEquals("홍길동", response.getName());
        assertEquals(Role.USER, response.getRole());
        assertEquals(Status.ACTIVE, response.getStatus());
    }

    @Test
    void testUpdateMember() throws Exception {
        // Mock 요청 및 응답 데이터 준비
        MemberUpdateRequest request = MemberUpdateRequest.builder()
                .name("김철수")
                .currentPassword("oldPassword")
                .password("newPassword")
                .contact("010-9876-5432")
                .build();

        MemberResponse mockResponse = MemberResponse.builder()
                .memberId(1L)
                .name("김철수")
                .contact("010-9876-5432")
                .build();

        // WireMock으로 API 응답 모킹
        wireMockServer.stubFor(WireMock.put("/api/members")
                .withRequestBody(WireMock.equalToJson(objectMapper.writeValueAsString(request)))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(mockResponse))));

        // 테스트 실행
        MemberResponse response = memberServiceClient.updateMember(request);

        // 검증
        assertNotNull(response);
        assertEquals("김철수", response.getName());
        assertEquals("010-9876-5432", response.getContact());
    }
}
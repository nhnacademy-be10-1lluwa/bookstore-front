package com.nhnacademy.illuwa.member.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.nhnacademy.illuwa.member.dto.MemberResponse;
import com.nhnacademy.illuwa.member.enums.Role;
import com.nhnacademy.illuwa.member.enums.Status;
import com.nhnacademy.illuwa.memberaddress.dto.PageResponse;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.test.context.TestPropertySource;
import com.github.tomakehurst.wiremock.client.WireMock;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@EnableFeignClients
@TestPropertySource(properties = {
        "spring.security.enabled=false", // Security 비활성화
        "api.base-url=http://localhost:8089"
})
class AdminMemberServiceClientTest {

    private static WireMockServer wireMockServer;

    @Autowired
    private AdminMemberServiceClient adminMemberServiceClient;

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
    void testGetPagedMemberList() throws Exception {
        // Mock 데이터 준비
        MemberResponse member = MemberResponse.builder()
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

        PageResponse<MemberResponse> mockResponse = new PageResponse<>();
        mockResponse.setContent(Collections.singletonList(member));
        mockResponse.setTotalPages(1);
        mockResponse.setSize(10);
        mockResponse.setTotalPages(1);
        mockResponse.setTotalElements(1);

        // WireMock으로 API 응답 모킹Hunt
        wireMockServer.stubFor(WireMock.get("/api/admin/members/paged?page=1&size=10")
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(mockResponse))));

        // 테스트 실행
        PageResponse<MemberResponse> response = adminMemberServiceClient.getPagedMemberList(1, 10);

        // 검증
        assertNotNull(response);
        assertEquals(1, response.getContent().size());
        assertEquals("홍길동", response.getContent().get(0).getName());
        assertEquals(1, response.getTotalPages());
        assertEquals(10, response.getSize());
    }
}
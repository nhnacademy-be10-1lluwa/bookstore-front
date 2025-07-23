package com.nhnacademy.illuwa.member.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.nhnacademy.illuwa.common.dto.PageResponse;
import com.nhnacademy.illuwa.member.enums.GradeName;
import com.nhnacademy.illuwa.member.dto.MemberResponse;
import com.nhnacademy.illuwa.member.enums.Role;
import com.nhnacademy.illuwa.member.enums.Status;
import com.nhnacademy.illuwa.pointhistory.dto.PointHistoryResponse;
import com.nhnacademy.illuwa.pointhistory.enums.PointHistoryType;
import com.nhnacademy.illuwa.pointhistory.enums.PointReason;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.nhnacademy.illuwa.member.enums.GradeName.BASIC;

@SpringBootTest(
        properties = {
                "api.base-url=http://localhost:9876"
        },
        webEnvironment = SpringBootTest.WebEnvironment.NONE
)
public class AdminMemberServiceClientTest {
    static WireMockServer wireMockServer;
    static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule()).configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);


    @Autowired
    AdminMemberServiceClient client;

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
    @DisplayName("관리자 회원 관리 작동 확인")
    void testGetPagedMembers() throws Exception {
        PageResponse<MemberResponse> mockPageResponse = new PageResponse<>(
                List.of(
                        MemberResponse.builder()
                                .memberId(1L)
                                .paycoId("payco123")
                                .name("홍길동")
                                .birth(LocalDate.of(1990, 1, 1))
                                .email("hong@sample.com")
                                .role(Role.USER)
                                .contact("010-1234-5678")
                                .gradeName("일반")
                                .point(BigDecimal.valueOf(10000))
                                .status(Status.ACTIVE)
                                .createdAt(LocalDateTime.now().minusYears(1))
                                .lastLoginAt(LocalDateTime.now())
                                .build(),
                        MemberResponse.builder()
                                .memberId(2L)
                                .paycoId("payco123")
                                .name("임꺽정")
                                .birth(LocalDate.of(1990, 2, 2))
                                .email("imggukjung@sample.com")
                                .role(Role.USER)
                                .contact("010-1234-5678")
                                .gradeName("골드")
                                .point(BigDecimal.valueOf(10000))
                                .status(Status.ACTIVE)
                                .createdAt(LocalDateTime.now().minusYears(1))
                                .lastLoginAt(LocalDateTime.now())
                                .build()
                ),
                5,
                50L,
                1,
                10,
                false,
                true
        );

        wireMockServer.stubFor(WireMock.get(WireMock.urlPathEqualTo("/api/admin/members"))
                .withQueryParam("grade", WireMock.matching(".*"))
                .withQueryParam("page", WireMock.matching("[0-9]+"))
                .withQueryParam("size", WireMock.matching("[0-9]+"))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(mockPageResponse))
                        .withStatus(200))
        );

        PageResponse<MemberResponse> result = client.getPagedMemberList(BASIC, 1, 10);
        Assertions.assertEquals(mockPageResponse, result);

    }

    @Test
    @DisplayName("등급별 포인트 지급 작동 확인")
    void testGivePointToGrade() throws Exception {
        List<PointHistoryResponse> mockResponse = List.of(
                new PointHistoryResponse(
                        1L,
                        PointHistoryType.EARN,
                        PointReason.GRADE_EVENT,
                        BigDecimal.valueOf(1000),
                        BigDecimal.valueOf(6000),
                        LocalDateTime.now()
                ),
                new PointHistoryResponse(
                        2L,
                        PointHistoryType.EARN,
                        PointReason.GRADE_EVENT,
                        BigDecimal.valueOf(1000),
                        BigDecimal.valueOf(6000),
                        LocalDateTime.now()
                )
        );

        String gradeName = BASIC.name();

        wireMockServer.stubFor(WireMock.post(WireMock.urlPathEqualTo("/api/members/grades/" + gradeName + "/points"))
                .withQueryParam("point", WireMock.matching("\\d+(\\.\\d+)?"))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(mockResponse))
                        .withStatus(200))
        );

        List<PointHistoryResponse> result = client.givePointToGrade(BASIC, BigDecimal.valueOf(1000));
        Assertions.assertEquals(mockResponse, result);


    }
}
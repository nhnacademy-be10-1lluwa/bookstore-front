package com.nhnacademy.illuwa.member.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.nhnacademy.illuwa.auth.dto.payco.PaycoMemberUpdateRequest;
import com.nhnacademy.illuwa.member.dto.MemberResponse;
import com.nhnacademy.illuwa.member.dto.MemberUpdateRequest;
import com.nhnacademy.illuwa.member.dto.PasswordCheckRequest;
import com.nhnacademy.illuwa.member.enums.Role;
import com.nhnacademy.illuwa.member.enums.Status;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;

@SpringBootTest(
        properties = {
                "api.base-url=http://localhost:9877"
        },
        webEnvironment = SpringBootTest.WebEnvironment.NONE
)
public class MemberServiceClientTest {
    static WireMockServer wireMockServer;
    static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule()).configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);


    @Autowired
    MemberServiceClient client;

    @MockBean
    ClientRegistrationRepository clientRegistrationRepository;

    @BeforeAll
    static void startWireMock() {
        wireMockServer = new WireMockServer(9877);
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
    @DisplayName("회원 단일 조회 동작 확인")
    void testGetMember() throws Exception {
        MemberResponse memberResponse = MemberResponse.builder()
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
                .build();

        wireMockServer.stubFor(WireMock.get("/api/members")
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(memberResponse))
                        .withStatus(200))
        );

        MemberResponse result = client.getMember();
        Assertions.assertEquals(memberResponse, result);
    }

    @Test
    @DisplayName("회원 수정 동작 확인")
    void testUpdateMember() throws Exception {
        MemberResponse memberResponse = MemberResponse.builder()
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
                .build();
        MemberUpdateRequest memberUpdateRequest =
                new MemberUpdateRequest("임꺽정", "currentPassword1234!", "updatePassword1234!", "010-1111-1111");

        wireMockServer.stubFor(WireMock.put("/api/members")
                .withRequestBody(equalToJson(objectMapper.writeValueAsString(memberUpdateRequest)))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(memberResponse))
                        .withStatus(200)
                )
        );

        MemberResponse result = client.updateMember(memberUpdateRequest);
        Assertions.assertEquals(memberResponse, result);
    }

    @Test
    @DisplayName("회원 삭제 동작 확인")
    void testDeleteMember() throws Exception {
        wireMockServer.stubFor(WireMock.delete("/api/members")
            .willReturn(WireMock.aResponse()
                .withHeader("Content-Type", "application/json")
                .withStatus(200)
            )
        );

        client.deleteMember();
    }

    @Test
    @DisplayName("비밀번호 검증 동작 확인")
    void testCheckPassword() throws Exception {
        PasswordCheckRequest passwordCheckRequest = PasswordCheckRequest.builder()
                .inputPassword("123456")
                .build();

        wireMockServer.stubFor(WireMock.post("/api/members/password-check")
                .withRequestBody(equalToJson(objectMapper.writeValueAsString(passwordCheckRequest)))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("true")
                        .withStatus(200)
                )
        );

        boolean result = client.checkPassword(passwordCheckRequest);
        Assertions.assertTrue(result);
    }

    @Test
    @DisplayName("페이코 회원 초기 정보 설정 동작 확인")
    void testUpdatePaycoMember() throws Exception {
        PaycoMemberUpdateRequest paycoMemberUpdateRequest = new PaycoMemberUpdateRequest(
                "페이코회원",
                LocalDate.of(1990, 1, 1),
                "test@payco.test",
                "010-1234-1234"
        );

        wireMockServer.stubFor(WireMock.put("/api/members/internal/social-members")
                .withRequestBody(equalToJson(objectMapper.writeValueAsString(paycoMemberUpdateRequest)))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBody("")
                )
        );

        client.updatePaycoMember(paycoMemberUpdateRequest);
    }

    @Test
    @DisplayName("리스트에서 회원이름 불러오기 동작 확인")
    void testGetNamesFromIdList() throws Exception {
        Map<Long, String> responseMap = new HashMap<Long, String>();
        responseMap.put(1L, "홍길동");

        List<Long> reviewersIdList = new ArrayList<Long>();
        reviewersIdList.add(1L);

        wireMockServer.stubFor(WireMock.get(WireMock.urlMatching("/api/members/names\\?member-ids=1(&member-ids=\\d+)*"))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(responseMap))
                        .withStatus(200))
        );


        Map<Long, String> result = client.getNamesFromIdList(reviewersIdList);
        Assertions.assertEquals(responseMap, result);
    }

}



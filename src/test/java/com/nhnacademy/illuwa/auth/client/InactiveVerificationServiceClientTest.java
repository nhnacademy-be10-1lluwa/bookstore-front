//package com.nhnacademy.illuwa.auth.client;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.github.tomakehurst.wiremock.WireMockServer;
//import com.nhnacademy.illuwa.auth.dto.message.*;
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.cloud.openfeign.EnableFeignClients;
//import org.springframework.test.context.ActiveProfiles;
//
//import static com.github.tomakehurst.wiremock.client.WireMock.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest(
//        properties = {"api.base-url=http://localhost:8889"},
//        webEnvironment = SpringBootTest.WebEnvironment.NONE
//)
//@EnableFeignClients(basePackageClasses = InactiveVerificationServiceClient.class)
//@ActiveProfiles("test")
//public class InactiveVerificationServiceClientTest {
//
//    static WireMockServer wireMockServer;
//    static ObjectMapper mapper = new ObjectMapper();
//
//    @Autowired
//    InactiveVerificationServiceClient client;
//
//    @BeforeAll
//    static void startWireMock() {
//        wireMockServer = new WireMockServer(8889);
//        wireMockServer.start();
//    }
//
//    @AfterAll
//    static void stopWireMock() {
//        wireMockServer.stop();
//    }
//
//    @BeforeEach
//    void setup() {
//        wireMockServer.resetAll();
//    }
//
//    @Test
//    void testGetInactiveMemberInfo() throws Exception {
//        String reqEmail = "inactive@test.com";
//        SendVerificationRequest req = new SendVerificationRequest(reqEmail);
//
//        InactiveCheckResponse res = InactiveCheckResponse.builder()
//                .status(com.nhnacademy.illuwa.member.enums.Status.INACTIVE)
//                .name("inactive")
//                .email(reqEmail)
//                .contact("010-1111-1111").build();
//
//        wireMockServer.stubFor(post(urlEqualTo("/api/members/check-status"))
//                .willReturn(aResponse().withHeader("Content-Type", "application/json")
//                        .withBody(mapper.writeValueAsString(res)).withStatus(200)));
//
//        InactiveCheckResponse response = client.getInactiveMemberInfo(req);
//
//        assertEquals(res.getStatus(), response.getStatus());
//        assertEquals(res.getEmail(), response.getEmail());
//        assertEquals(res.getName(), response.getName());
//        assertEquals(res.getContact(), response.getContact());
//    }
//}

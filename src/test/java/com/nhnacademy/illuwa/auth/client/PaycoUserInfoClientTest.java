//package com.nhnacademy.illuwa.auth.client;
//
//import com.nhnacademy.illuwa.config.RestTemplateTestConfig;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Import;
//import org.springframework.http.*;
//import org.springframework.test.web.client.MockRestServiceServer;
//import org.springframework.beans.factory.annotation.Value;
//
//import java.util.Map;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
//import static org.springframework.test.web.client.response.MockRestResponseCreators.*;
//
//@Import(RestTemplateTestConfig.class)
//@RestClientTest(PaycoUserInfoClient.class)
//public class PaycoUserInfoClientTest {
//    @Autowired
//    private PaycoUserInfoClient paycoUserInfoClient;
//
//    @Autowired
//    private MockRestServiceServer mockServer;
//
//    @Value("${spring.security.oauth2.client.registration.payco.client-id:test-client-id}")
//    private String clientId;
//
//    @BeforeEach
//    void setup() {
//        mockServer.reset();
//    }
//
//    @Test
//    @DisplayName("getUserInfo")
//    void testGetUserInfo() {
//        String accessToken = "mock-access-token";
//
//        String fakeResponse = """
//        {
//          "header": {
//            "isSuccessful": true
//          },
//          "data": {
//            "member": {
//              "id": "payco123",
//              "email": "user@payco.com",
//              "nickname": "riveroad"
//            }
//          }
//        }
//        """;
//
//        mockServer.expect(requestTo("https://apis-payco.krp.toastoven.net/payco/friends/find_member_v2.json"))
//                .andExpect(method(HttpMethod.POST))
//                .andExpect(header("client_id", clientId))
//                .andExpect(header("access_token", accessToken))
//                .andRespond(withSuccess(fakeResponse, MediaType.APPLICATION_JSON));
//
//        // then: �묐떟 寃곌낵瑜� 寃�利�
//        Map<String, Object> userInfo = paycoUserInfoClient.getUserInfo(accessToken);
//
//        assertThat(userInfo).isNotNull();
//        assertThat(userInfo.get("id")).isEqualTo("payco123");
//        assertThat(userInfo.get("email")).isEqualTo("user@payco.com");
//        assertThat(userInfo.get("nickname")).isEqualTo("riveroad");
//    }
//}
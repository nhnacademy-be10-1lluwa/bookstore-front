package com.nhnacademy.illuwa.memberaddress.client;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.nhnacademy.illuwa.common.dto.PageResponse;
import com.nhnacademy.illuwa.memberaddress.dto.MemberAddressRequest;
import com.nhnacademy.illuwa.memberaddress.dto.MemberAddressResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

import java.util.List;

@SpringBootTest(
        properties = {
                "api.base-url=http://localhost:9878"
        },
        webEnvironment = SpringBootTest.WebEnvironment.NONE
)
public class MemberAddressServiceClientTest {
    static WireMockServer wireMockServer;
    static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule()).configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);


    @Autowired
    MemberAddressServiceClient client;

    @MockBean
    ClientRegistrationRepository clientRegistrationRepository;

    @BeforeAll
    static void startWireMock() {
        wireMockServer = new WireMockServer(9878);
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
    @DisplayName("주소 목록 get 동작 확인")
    public void testGetAddressList() throws Exception {
        List<MemberAddressResponse> addressList = List.of(
                MemberAddressResponse.builder()
                        .memberId(1L)
                        .memberAddressId(1L)
                        .addressName("test name")
                        .recipientName("test name")
                        .recipientContact("test contact")
                        .postCode("11111")
                        .address("test address")
                        .detailAddress("test detail address")
                        .defaultAddress(false)
                        .forcedDefaultAddress(false)
                        .build(),
                MemberAddressResponse.builder()
                        .memberId(2L)
                        .memberAddressId(2L)
                        .addressName("test name")
                        .recipientName("test name")
                        .recipientContact("test contact")
                        .postCode("22222")
                        .address("test address")
                        .detailAddress("test detail address")
                        .defaultAddress(false)
                        .forcedDefaultAddress(false)
                        .build()
        );

        wireMockServer.stubFor(WireMock.get(WireMock.urlPathEqualTo("/api/members/addresses"))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(addressList))
                        .withStatus(200))
        );

        List<MemberAddressResponse> result = client.getAddressList();
        Assertions.assertEquals(addressList, result);
    };

    @Test
    @DisplayName("주소 개수 get 동작 확인")
    public void getAddressCount() {
        wireMockServer.stubFor(WireMock.get(WireMock.urlPathEqualTo("/api/members/addresses/count"))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("5")
                        .withStatus(200)));

        int count = client.getAddressCount();
        Assertions.assertEquals(5, count);
    }

    @Test
    @DisplayName("페이지화 된 주소 목록 get 동작 확인")
    public void getPagedAddressList() throws Exception {
        List<MemberAddressResponse> content = List.of(
                MemberAddressResponse.builder()
                        .memberId(1L)
                        .memberAddressId(1L)
                        .addressName("test name")
                        .recipientName("test name")
                        .recipientContact("test contact")
                        .postCode("11111")
                        .address("test address")
                        .detailAddress("test detail address")
                        .defaultAddress(false)
                        .forcedDefaultAddress(false)
                        .build(),
                MemberAddressResponse.builder()
                        .memberId(2L)
                        .memberAddressId(2L)
                        .addressName("test name2")
                        .recipientName("test name2")
                        .recipientContact("test contact2")
                        .postCode("22222")
                        .address("test address2")
                        .detailAddress("test detail address2")
                        .defaultAddress(false)
                        .forcedDefaultAddress(false)
                        .build()
        );

        PageResponse<MemberAddressResponse> pageResponse = new PageResponse<>(
                content,
                3,
                6L,
                0,
                2,
                false,
                true
        );

        wireMockServer.stubFor(WireMock.get(WireMock.urlPathEqualTo("/api/members/addresses/paged"))
                .withQueryParam("page", WireMock.equalTo("0"))
                .withQueryParam("size", WireMock.equalTo("2"))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(pageResponse))
                        .withStatus(200)));

        PageResponse<MemberAddressResponse> result = client.getPagedAddressList(0, 2);
        Assertions.assertEquals(pageResponse, result);
    }

    @Test
    @DisplayName("주소 get 동작 확인")
    public void getAddress() throws Exception {
        long addressId = 1L;
        MemberAddressResponse address = MemberAddressResponse.builder()
                .memberId(1L)
                .memberAddressId(addressId)
                .addressName("test")
                .recipientName("test")
                .recipientContact("contact")
                .postCode("12345")
                .address("test address")
                .detailAddress("test detail address")
                .defaultAddress(false)
                .forcedDefaultAddress(false)
                .build();

        wireMockServer.stubFor(WireMock.get(WireMock.urlPathEqualTo("/api/members/addresses/" + addressId))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(address))
                        .withStatus(200)));

        MemberAddressResponse result = client.getAddress(addressId);
        Assertions.assertEquals(address, result);
    }

    @Test
    @DisplayName("주소 생성 동작 확인")
    public void createAddress() throws Exception {
        MemberAddressRequest request = MemberAddressRequest.builder()
                .postCode("12345")
                .address("서울시 강남구 강남대로 1")
                .detailAddress("101동 202호")
                .addressName("우리집")
                .recipientName("홍길동")
                .recipientContact("010-1234-5678")
                .defaultAddress(true)
                .build();

        MemberAddressResponse response = MemberAddressResponse.builder()
                .memberId(1L)
                .memberAddressId(100L)
                .addressName("add")
                .recipientName("recipient")
                .recipientContact("contact")
                .postCode("11111")
                .address("address")
                .detailAddress("detail")
                .defaultAddress(true)
                .forcedDefaultAddress(false)
                .build();

        wireMockServer.stubFor(WireMock.post(WireMock.urlPathEqualTo("/api/members/addresses"))
                .withRequestBody(WireMock.equalToJson(objectMapper.writeValueAsString(request)))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(response))
                        .withStatus(200)));

        MemberAddressResponse result = client.createAddress(request);
        Assertions.assertEquals(response, result);
    }

    @Test
    @DisplayName("주소 update 동작 확인")
    public void updateAddress() throws Exception {
        long addressId = 1L;
        MemberAddressRequest request = MemberAddressRequest.builder()
                .postCode("54321")
                .address("서울시 마포구 월드컵북로 400")
                .detailAddress("202동 1001호")
                .addressName("회사")
                .recipientName("김철수")
                .recipientContact("010-8765-4321")
                .defaultAddress(false)
                .build();

        MemberAddressResponse response = MemberAddressResponse.builder()
                .memberId(1L)
                .memberAddressId(addressId)
                .addressName("updated name")
                .recipientName("updated name")
                .recipientContact("updated contact")
                .postCode("99999")
                .address("updated address")
                .detailAddress("updated detail address")
                .defaultAddress(true)
                .forcedDefaultAddress(false)
                .build();

        wireMockServer.stubFor(WireMock.post(WireMock.urlPathEqualTo("/api/members/addresses/" + addressId))
                .withRequestBody(WireMock.equalToJson(objectMapper.writeValueAsString(request)))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(response))
                        .withStatus(200)));

        MemberAddressResponse result = client.updateAddress(request, addressId);
        Assertions.assertEquals(response, result);
    }

    @Test
    @DisplayName("주소 delete 동작 확인")
    public void deleteAddress() {
        long addressId = 10L;

        wireMockServer.stubFor(WireMock.delete(WireMock.urlPathEqualTo("/api/members/addresses/" + addressId))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)));

        client.deleteAddress(addressId);
    }

    @Test
    @DisplayName("주소 기본값 설정 동작 확인")
    public void setDefaultAddress() {
        long addressId = 5L;

        wireMockServer.stubFor(WireMock.post(WireMock.urlPathEqualTo("/api/members/addresses/set-default/" + addressId))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)));

        client.setDefaultAddress(addressId);
    }
}

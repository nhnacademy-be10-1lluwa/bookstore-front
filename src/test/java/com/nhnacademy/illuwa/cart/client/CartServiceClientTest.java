package com.nhnacademy.illuwa.cart.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.nhnacademy.illuwa.cart.dto.BookCartRequest;
import com.nhnacademy.illuwa.cart.dto.BookCartResponse;
import com.nhnacademy.illuwa.cart.dto.CartResponse;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

import java.math.BigDecimal;
import java.util.Arrays;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(
        properties = {
                "api.base-url=http://localhost:9876"
        },
        webEnvironment = SpringBootTest.WebEnvironment.NONE
)
public class CartServiceClientTest {

    static WireMockServer wireMockServer;
    static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule()).configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);


    @Autowired
    CartServiceClient cartServiceClient;

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
    @DisplayName("장바구니 조회")
    void getCart_success() throws Exception{
        BookCartResponse testBook1 = new BookCartResponse(1L, "Test Book 1", 2, 10000, "http://example.com/img1.jpg");
        BookCartResponse testBook2 = new BookCartResponse(2L, "Test Book 2", 1, 5000, "http://example.com/img2.jpg");

        BigDecimal totalPrice = BigDecimal.valueOf(testBook1.getSalePrice())
                .multiply(BigDecimal.valueOf(testBook1.getAmount()))
                .add(BigDecimal.valueOf(testBook2.getSalePrice())
                        .multiply(BigDecimal.valueOf(testBook2.getAmount())));


        CartResponse testCart = new CartResponse(
                1L,
                Arrays.asList(testBook1, testBook2),
                totalPrice
        );

        wireMockServer.stubFor(get(urlPathEqualTo("/api/cart"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(testCart))
                        .withStatus(200))
        );

        CartResponse cartResponse = cartServiceClient.getCart();

        assertEquals(cartResponse, testCart);
    }

    @Test
    @DisplayName("장바구니 물품 전체 삭제")
    void clearCart_success() {
        wireMockServer.stubFor(delete(urlPathEqualTo("/api/cart"))
                .willReturn(aResponse()
                        .withStatus(200))
        );

        Assertions.assertDoesNotThrow(() -> cartServiceClient.clearCart());

        wireMockServer.verify(deleteRequestedFor(urlEqualTo("/api/cart")));
    }

    @Test
    @DisplayName("장바구니 물품 추가 성공")
    void addBookCart_success() throws Exception {
        BookCartRequest testRequest = new BookCartRequest(1L,1L, 3);

        wireMockServer.stubFor(post(urlPathEqualTo("/api/cart/book"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(testRequest))
                        .withStatus(200))
        );

        BookCartRequest returnedRequest = cartServiceClient.addBookCart(testRequest);

        assertEquals(returnedRequest, testRequest);
    }

    @Test
    @DisplayName("장바구니 물품 개별 삭제")
    void removeBookCart_success() throws Exception {
        BookCartRequest testRequest = new BookCartRequest(1L,1L, 3);

        wireMockServer.stubFor(delete(urlPathEqualTo("/api/cart/book"))
                .willReturn(aResponse()
                        .withBody(objectMapper.writeValueAsString(testRequest))
                        .withStatus(200))
        );

        Assertions.assertDoesNotThrow(() -> cartServiceClient.removeBookCart(testRequest));

        wireMockServer.verify(deleteRequestedFor(urlEqualTo("/api/cart/book"))
                .withRequestBody(equalToJson(objectMapper.writeValueAsString(testRequest))));
    }



}


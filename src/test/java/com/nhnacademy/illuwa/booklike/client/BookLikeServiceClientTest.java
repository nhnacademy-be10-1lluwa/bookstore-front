package com.nhnacademy.illuwa.booklike.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.nhnacademy.illuwa.book.dto.SimpleBookResponse;
import com.nhnacademy.illuwa.common.dto.PageResponse;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

@SpringBootTest(
        properties = {
                "api.base-url=http://localhost:9876"
        },
        webEnvironment = SpringBootTest.WebEnvironment.NONE
)
public class BookLikeServiceClientTest {

    static WireMockServer wireMockServer;
    static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule()).configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    @Autowired
    BookLikeServiceClient bookLikeServiceClient;

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
    @DisplayName("좋아요 여부 확인 -> Boolean 변환(true)")
    void isLikedByMeTest_True() throws Exception {
        long testBookId = 1L;

        wireMockServer.stubFor(get(WireMock.urlPathEqualTo("/api/book-likes"))
                .withQueryParam("book-id", equalTo(String.valueOf(testBookId)))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("true")
                        .withStatus(200))
        );

        Boolean result = bookLikeServiceClient.isLikedByMe(testBookId);

        assertTrue(result);
    }

    @Test
    @DisplayName("좋아요 여부 확인 -> Boolean 변환(False)")
    void isLikedByMeTest_False() throws Exception {
        long testBookId = 1L;

        wireMockServer.stubFor(get(urlPathEqualTo("/api/book-likes"))
                .withQueryParam("book-id", equalTo(String.valueOf(testBookId)))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("false")
                        .withStatus(200))
        );

        Boolean result = bookLikeServiceClient.isLikedByMe(testBookId);

        assertFalse(result);
    }

    @Test
    @DisplayName("좋아요 클릭")
    void toggleBookLikesTest() throws Exception {
        long testBookId = 1L;

        wireMockServer.stubFor(post(urlPathEqualTo("/api/book-likes"))
                .withQueryParam("book-id", equalTo(String.valueOf(testBookId)))
                .willReturn(aResponse()
                        .withStatus(200))
        );

        assertDoesNotThrow(() -> bookLikeServiceClient.toggleBookLikes(testBookId));

        wireMockServer.verify(postRequestedFor(urlPathEqualTo("/api/book-likes"))
                .withQueryParam("book-id", equalTo(String.valueOf(testBookId))));
    }

    @Test
    @DisplayName("좋아요한 도서 목록 조회")
    void getLikedBooksByMemberTest() throws Exception {
        int page = 0;
        int size = 10;

        SimpleBookResponse testBook1 = new SimpleBookResponse(
                1L,
                "Test Book 1",
                "Author 1",
                "Publish 1",
                "Description 1",
                "ISBN-01",
                BigDecimal.valueOf(10000),
                BigDecimal.valueOf(15000),
                "http://example.com/cover1.jpg",
                "NORMAL"
        );

        SimpleBookResponse testBook2 = new SimpleBookResponse(
                2L,
                "Test Book 2",
                "Author 2",
                "Publish 2",
                "Description 2",
                "ISBN-02",
                BigDecimal.valueOf(5000),
                BigDecimal.valueOf(10000),
                "http://example.com/cover1.jpg",
                "NORMAL"
        );

        List<SimpleBookResponse> testBooks = Arrays.asList(testBook1, testBook2);

        PageResponse<SimpleBookResponse> pageResponse = new PageResponse<>(
                testBooks,
                1,
                (long) testBooks.size(),
                page,
                size,
                true,
                true
        );

        String expectedResponseBody = objectMapper.writeValueAsString(pageResponse);

        wireMockServer.stubFor(get(urlPathEqualTo("/api/book-likes/list"))
                .withQueryParam("page", equalTo(String.valueOf(page)))
                .withQueryParam("size", equalTo(String.valueOf(size)))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(expectedResponseBody)
                        .withStatus(200))
        );

        PageResponse<SimpleBookResponse> result = bookLikeServiceClient.getLikedBooksByMember(page, size);

        assertEquals(pageResponse, result);

        wireMockServer.verify(getRequestedFor(urlPathEqualTo("/api/book-likes/list"))
                .withQueryParam("page", equalTo(String.valueOf(page)))
                .withQueryParam("size", equalTo(String.valueOf(size))));

    }
}

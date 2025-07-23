package com.nhnacademy.illuwa.book.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.nhnacademy.illuwa.book.dto.*;
import com.nhnacademy.illuwa.category.dto.CategoryResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        properties = {
                "api.base-url=http://localhost:9880"
        },
        webEnvironment = SpringBootTest.WebEnvironment.NONE
)
public class ProductClientTest {
    static WireMockServer wireMockServer;
    static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule()).configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    @Autowired
    ProductServiceClient client;

    @MockBean
    ClientRegistrationRepository clientRegistrationRepository;

    @BeforeAll
    static void startWireMock() {
        wireMockServer = new WireMockServer(9880);
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
    void findBookById() throws Exception {
        BookDetailResponse mock = new BookDetailResponse(2L, "제목2", "본문", "설명", "저자", "출판사", "2023-01-02", "23456789",
                new BigDecimal("12000"), new BigDecimal("10000"), false, Arrays.asList("img2"), 3, "판매중"
        );
        stub("/api/books/2", mock);

        BookDetailResponse result = client.findBookById(2L);
        assertThat(result.getId()).isEqualTo(2L);
    }

    @Test
    void findBookByIsbn() throws Exception {
        BookDetailResponse mock = new BookDetailResponse(3L, "제목3", "본문", "설명", "저자", "출판사", "2023-02-01", "34567890",
                new BigDecimal("11000"), new BigDecimal("9000"), false, Arrays.asList("img3"), 7, "판매중"
        );
        stub("/api/books/isbn/34567890", mock);

        BookDetailResponse result = client.findBookByIsbn("34567890");
        assertThat(result.getIsbn()).isEqualTo("34567890");
    }

    @Test
    void findBookByApi() throws Exception {
        BookExternalResponse ext = new BookExternalResponse(
                "외부제목", "설명", "저자", "출판사", java.time.LocalDate.of(2024, 1, 3),
                "44556677", 15000, 13000, "coverUrl", "소설"
        );
        stub("/api/external-books/isbn/44556677", ext);

        BookExternalResponse result = client.findBookByApi("44556677");
        assertThat(result.getIsbn()).isEqualTo("44556677");
    }

    @Test
    void getBestSeller() throws Exception {
        BestSellerResponse b1 = new BestSellerResponse();
        b1.setTitle("베스트1"); b1.setAuthor("저자1"); b1.setIsbn("ISBN1");
        List<BestSellerResponse> list = List.of(b1);
        stubList("/api/books?type=bestseller", list);

        List<BestSellerResponse> result = client.getBestSeller();
        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getTitle()).isEqualTo("베스트1");
    }



//    @Test
//    void getRegisteredBookPaged() throws Exception {
//        BookDetailResponse b1 = new BookDetailResponse(
//                1L, "제목1", "본문", "설명", "저자", "출판사", "2024-01-01", "111",
//                new BigDecimal("10000"), new BigDecimal("9000"), false, List.of("img"), 10, "판매중"
//        );
//        BookDetailResponse b2 = new BookDetailResponse(
//                2L, "제목2", "본문", "설명", "저자", "출판사", "2024-01-02", "222",
//                new BigDecimal("15000"), new BigDecimal("13500"), false, List.of("img2"), 7, "판매중"
//        );
//        List<BookDetailResponse> content = Arrays.asList(b1, b2);
//
//        PageImpl<BookDetailResponse> page = new PageImpl<>(
//                content,
//                PageRequest.of(0, 10),
//                2
//        );
//
//        String pageJson = objectMapper.writeValueAsString(page);
//
//        wireMockServer.stubFor(get(urlPathEqualTo("/api/books"))
//                .withQueryParam("page", equalTo("0"))
//                .withQueryParam("size", equalTo("10"))
//                .withQueryParam("sort", equalTo("id"))
//                .willReturn(aResponse()
//                        .withStatus(200)
//                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
//                        .withBody(pageJson)));
//
//        Page<BookDetailResponse> result = client.getRegisteredBookPaged(0, 10, "id");
//
//        assertThat(result).isNotNull();
//        assertThat(result.getContent()).hasSize(2);
//        assertThat(result.getContent().getFirst().getTitle()).isEqualTo("제목1");
//        assertThat(result.getTotalElements()).isEqualTo(2L);
//        assertThat(result.getNumber()).isEqualTo(0);
//        assertThat(result.getSize()).isEqualTo(10);
//    }

    @Test
    void searchAladinBooksByTitle() throws Exception {
        BookExternalResponse ext = new BookExternalResponse();
        ext.setTitle("알라딘도서");
        stubList("/api/admin/books/external?title=테스트", List.of(ext));

        List<BookExternalResponse> result = client.searchAladinBooksByTitle("테스트");
        assertThat(result.get(0).getTitle()).isEqualTo("알라딘도서");
    }

    @Test
    void registerBookByApi() throws Exception {
        BookApiRegisterRequest req = new BookApiRegisterRequest();
        stubPost("/api/admin/books/external", req);

        var response = client.registerBookByApi(req);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
    }

    @Test
    void getCategoryTree() throws Exception {
        // CategoryResponse의 children도 타입 명확화
        CategoryResponse novel = new CategoryResponse(2L, 1L, "소설", Collections.emptyList());
        CategoryResponse economy = new CategoryResponse(3L, 1L, "경제", Collections.emptyList());
        CategoryResponse root = new CategoryResponse(1L, null, "전체", Arrays.asList(novel, economy));
        List<CategoryResponse> tree = Collections.singletonList(root);

        wireMockServer.stubFor(get(urlEqualTo("/api/categories/tree"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(tree))));

        List<CategoryResponse> result = client.getCategoryTree();

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getCategoryName()).isEqualTo("전체");
        assertThat(result.getFirst().getChildren()).hasSize(2);
        assertThat(result.getFirst().getChildren().getFirst().getCategoryName()).isEqualTo("소설");
    }

    @Test
    void getAllCategories() throws Exception {
        CategoryResponse novel = new CategoryResponse(2L, 1L, "소설", Collections.emptyList());
        CategoryResponse economy = new CategoryResponse(3L, 1L, "경제", Collections.emptyList());
        List<CategoryResponse> list = Arrays.asList(novel, economy);

        wireMockServer.stubFor(get(urlEqualTo("/api/categories"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(list))));

        List<CategoryResponse> result = client.getAllCategories();

        assertThat(result).hasSize(2);
        assertThat(result.getFirst().getCategoryName()).isEqualTo("소설");
    }

    @Test
    void deleteBook() {
        wireMockServer.stubFor(delete(urlEqualTo("/api/admin/books/10")).willReturn(aResponse().withStatus(204)));
        client.deleteBook(10L);
    }

    // stupPatch 메서드 필요
//    @Test
//    void updateBook() throws Exception {
//        BookUpdateRequest updateReq = new BookUpdateRequest(); updateReq.setTitle("수정도서");
//        stub("/api/admin/books/10", updateReq);
//        client.updateBook(10L, updateReq);
//    }

    @Test
    void getBookDetailWithExtraInfo() throws Exception {
        BookDetailWithExtraInfoResponse detail = new BookDetailWithExtraInfoResponse();
        detail.setId(5L); detail.setTitle("부가정보도서");
        stub("/api/admin/books/5/detail", detail);

        BookDetailWithExtraInfoResponse result = client.getBookDetailWithExtraInfo(5L);
        assertThat(result.getTitle()).isEqualTo("부가정보도서");
    }

    @Test
    void addTagToBook() {
        wireMockServer.stubFor(post(urlEqualTo("/api/admin/books/2/tags/3")).willReturn(aResponse().withStatus(200)));
        client.addTagToBook(2L,3L);
    }

    @Test
    void removeTagFromBook() {
        wireMockServer.stubFor(delete(urlEqualTo("/api/admin/books/2/tags/3")).willReturn(aResponse().withStatus(204)));
        client.removeTagFromBook(2L,3L);
    }

    // ---------- 공통 stub 유틸리티 ----------
    private void stub(String url, Object obj) throws Exception {
        wireMockServer.stubFor(get(urlEqualTo(url))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(obj))));
    }
    private void stubList(String url, List<?> list) throws Exception {
        wireMockServer.stubFor(get(urlPathEqualTo(url.split("\\?")[0]))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(list))));
    }
    private void stubPost(String url, Object bodyObj) throws Exception {
        wireMockServer.stubFor(post(urlEqualTo(url))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(bodyObj != null ? bodyObj : ""))));
    }

}

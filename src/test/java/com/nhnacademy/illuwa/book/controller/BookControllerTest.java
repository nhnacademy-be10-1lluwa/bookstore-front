package com.nhnacademy.illuwa.book.controller;

import com.nhnacademy.illuwa.auth.utils.JwtCookieUtil;
import com.nhnacademy.illuwa.book.dto.BookDetailResponse;
import com.nhnacademy.illuwa.book.service.BookService;
import com.nhnacademy.illuwa.booklike.service.BookLikeService;
import com.nhnacademy.illuwa.common.dto.PageResponse;
import com.nhnacademy.illuwa.config.handler.CategoryControllerAdvice;
import com.nhnacademy.illuwa.member.service.MemberService;
import com.nhnacademy.illuwa.review.dto.ReviewResponse;
import com.nhnacademy.illuwa.review.service.ReviewLikeService;
import com.nhnacademy.illuwa.review.service.ReviewService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(controllers = BookController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {CategoryControllerAdvice.class})
        })
@AutoConfigureMockMvc(addFilters = false)
public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ClientRegistrationRepository clientRegistrationRepository;

    @MockBean
    BookService bookService;
    @MockBean
    ReviewService reviewService;
    @MockBean
    ReviewLikeService reviewLikeService;
    @MockBean
    MemberService memberService;
    @MockBean
    BookLikeService bookLikeService;

    private MockedStatic<JwtCookieUtil> staticMock;

    @BeforeEach
    void setUp() {
        staticMock = Mockito.mockStatic(JwtCookieUtil.class);
        staticMock.when(() -> JwtCookieUtil.checkAccessToken(any(HttpServletRequest.class)))
                .thenReturn(true);
    }

    @AfterEach
    void tearDown() {
        staticMock.close();
    }

    @Test
    @DisplayName("책 상세 검색 정상 작동")
    void testBookDetailBySearch() throws Exception {
        Long bookId = 1L;
        boolean isLoggedIn = true;

        BookDetailResponse bookDetail = new BookDetailResponse();
        bookDetail.setId(bookId);

        ReviewResponse review = new ReviewResponse(
                10L, "타이틀", "내용", 5, LocalDateTime.now(), bookId, 22L, List.of()
        );

        List<ReviewResponse> reviewList = List.of(review);
        PageResponse reviewPage = new PageResponse(
                reviewList,
                1,
                1L,
                0,
                5,
                true,
                true
        );

        Map<Long, String> reviewerNameMap = Map.of(22L, "홍길동");
        Map<Long, Long> likeCountMap = Map.of(10L, 7L);

        when(bookService.findBookById(bookId)).thenReturn(bookDetail);
        when(reviewService.getReviewPages(bookId, 0, 5)).thenReturn(reviewPage);
        when(memberService.getNamesFromIdList(List.of(22L))).thenReturn(reviewerNameMap);
        when(reviewLikeService.getLikeCountsFromReviews(List.of(10L))).thenReturn(likeCountMap);
        when(bookLikeService.isLikedByMe(bookId)).thenReturn(true);
        when(reviewLikeService.getMyLikedReviews(List.of(10L))).thenReturn(List.of(10L));

        mockMvc.perform(get("/books/{id}", bookId))
                .andExpect(status().isOk())
                .andExpect(view().name("book/detail"))
                .andExpect(model().attributeExists("book"))
                .andExpect(model().attributeExists("reviewContent"))
                .andExpect(model().attributeExists("reviewPage"))
                .andExpect(model().attributeExists("reviewerNameMap"))
                .andExpect(model().attributeExists("likeCountMap"));
//                .andExpect(model().attribute("isLoggedIn", isLoggedIn))
//                .andExpect(model().attribute("isMyLikedBook", true))
//                .andExpect(model().attribute("myLikedReviewIds", List.of(10L)));
    }

    @Test
    @DisplayName("isbn 책 상세 페이지 동작")
    void testBookDetail_isbn() throws Exception {
        String isbn = "1234567890";
        Long bookId = 1L;
        boolean isLoggedIn = false;

        BookDetailResponse bookDetail = new BookDetailResponse();
        bookDetail.setId(bookId);

        ReviewResponse review = new ReviewResponse(
                20L,
                "리뷰 제목",
                "리뷰 내용",
                4,
                LocalDateTime.now(),
                bookId,
                77L,
                List.of()
        );

        List<ReviewResponse> reviewList = List.of(review);

        PageResponse reviewPage = new PageResponse(
                reviewList,
                1,
                1L,
                0,
                5,
                true,
                true
        );

        Map<Long, String> reviewerNameMap = Map.of(77L, "김철수");
        Map<Long, Long> likeCountMap = Map.of(20L, 3L);

        when(bookService.findBookByIsbn(isbn)).thenReturn(bookDetail);
        when(reviewService.getReviewPages(bookId, 0, 5)).thenReturn(reviewPage);
        when(memberService.getNamesFromIdList(List.of(77L))).thenReturn(reviewerNameMap);
        when(reviewLikeService.getLikeCountsFromReviews(List.of(20L))).thenReturn(likeCountMap);

        mockMvc.perform(get("/books/isbn/{isbn}", isbn))
                .andExpect(status().isOk())
                .andExpect(view().name("book/detail"))
                .andExpect(model().attributeExists("book"))
                .andExpect(model().attributeExists("reviewContent"))
                .andExpect(model().attributeExists("reviewPage"))
                .andExpect(model().attributeExists("reviewerNameMap"))
                .andExpect(model().attributeExists("likeCountMap"));
//                .andExpect(model().attribute("isLoggedIn", isLoggedIn))
//                .andExpect(model().attributeDoesNotExist("isMyLikedBook"))
//                .andExpect(model().attributeDoesNotExist("myLikedReviewIds"));

    }

}

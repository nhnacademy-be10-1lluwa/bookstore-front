package com.nhnacademy.illuwa.booklike.controller;


import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.nhnacademy.illuwa.admin.controller.AdminMemberController;
import com.nhnacademy.illuwa.book.dto.SimpleBookResponse;
import com.nhnacademy.illuwa.booklike.client.BookLikeServiceClient;
import com.nhnacademy.illuwa.booklike.service.BookLikeService;
import com.nhnacademy.illuwa.cart.dto.BookCartResponse;
import com.nhnacademy.illuwa.common.dto.PageResponse;
import com.nhnacademy.illuwa.config.handler.CategoryControllerAdvice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = BookLikeController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {CategoryControllerAdvice.class})
        })
@AutoConfigureMockMvc(addFilters = false)
public class BookLikeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ClientRegistrationRepository clientRegistrationRepository;

    @MockBean
    private BookLikeService bookLikeService;
    private BookLikeServiceClient bookLikeServiceClient;
    @Autowired
    private ParameterNamesModule parameterNamesModule;

    @Test
    @DisplayName("좋아요 도서 목록 조회 성공")
    void getBookLikeList_Success() throws Exception {

        int page = 0;
        int size = 5;

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

        List<SimpleBookResponse> testBookList = Collections.singletonList(testBook1);

        PageResponse<SimpleBookResponse> pageResponse = new PageResponse<>(
                testBookList,
                1,
                testBookList.size(),
                page,
                size,
                true,
                true
        );

        when(bookLikeService.getLikedBooksByMember(eq(page), eq(size))).thenReturn(pageResponse);

        mockMvc.perform(get("/book-likes")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size)))
                .andExpect(status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.view().name("mypage/section/book_likes"))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.model().attributeExists("likedBooks", "activeMenu"));

        verify(bookLikeService, times(1)).getLikedBooksByMember(page, size);
    }

    @Test
    @DisplayName("좋아요 클릭")
    void toggleBookLikesTest() throws Exception {
        long bookId = 1L;

        mockMvc.perform(post("/book-likes")
                        .param("book-id", String.valueOf(bookId)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books/" + bookId));

        verify(bookLikeService, times(1)).toggleBookLikes(eq(bookId));
    }

}

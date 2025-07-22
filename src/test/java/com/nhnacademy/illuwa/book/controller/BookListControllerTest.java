package com.nhnacademy.illuwa.book.controller;

import com.nhnacademy.illuwa.book.dto.BookDetailResponse;
import com.nhnacademy.illuwa.book.service.BookService;
import com.nhnacademy.illuwa.config.handler.CategoryControllerAdvice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(controllers = BookListController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {CategoryControllerAdvice.class})
        })
@AutoConfigureMockMvc(addFilters = false)
public class BookListControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    BookService bookService;

    @MockBean
    ClientRegistrationRepository clientRegistrationRepository;

    @Test
    @DisplayName("GET /books 리스트 기본 조회")
    void listBooks_returnPageAndSort() throws Exception {
        BookDetailResponse bookDetail = new BookDetailResponse();
        bookDetail.setImageUrls(new ArrayList<>());
        List<BookDetailResponse> content = Collections.singletonList(bookDetail);
        Page<BookDetailResponse> bookPage = new PageImpl<>(content, PageRequest.of(0, 15, Sort.by("id").ascending()), 1);

        given(bookService.getPagedBooks(anyInt(), anyInt(), anyString()))
                .willReturn(bookPage);

        mockMvc.perform(get("/books")
                        .param("page", "0")
                        .param("size", "15")
                        .param("sort", "id,asc"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("bookPage"))
                .andExpect(model().attribute("sort", "id,asc"))
                .andExpect(view().name("book/book_list"));
    }
}

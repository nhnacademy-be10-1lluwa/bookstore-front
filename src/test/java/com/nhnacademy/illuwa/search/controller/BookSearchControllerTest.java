package com.nhnacademy.illuwa.search.controller;

import com.nhnacademy.illuwa.config.handler.CategoryControllerAdvice;
import com.nhnacademy.illuwa.search.dto.BookDocument;
import com.nhnacademy.illuwa.search.service.BookSearchService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.*;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = BookSearchController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {CategoryControllerAdvice.class})
        })
@AutoConfigureMockMvc(addFilters = false)
public class BookSearchControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookSearchService bookSearchService;

    private BookDocument createBook(Long id, String url) {
        return BookDocument.builder()
                .id(id)
                .title("Title " + id)
                .description("Description " + id)
                .author("Author " + id)
                .publisher("Publisher " + id)
                .isbn("ISBN" + id)
                .salePrice(BigDecimal.valueOf(1000 + id))
                .thumbnailUrl(url)
                .publishedDate(LocalDate.of(2020, 1, 1).plusDays(id))
                .build();
    }

    @DisplayName("키워드로 도서 검색 - 결과와 모델 검증")
    @Test
    void test_searchBooks_withKeyword() throws Exception {
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id"));
        List<BookDocument> books = List.of(createBook(1L, null), createBook(2L, null));
        Page<BookDocument> page = new PageImpl<>(books, pageable, 2);

        when(bookSearchService.searchBooks(eq("java"), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/books/search-es")
                        .param("keyword", "java")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("book/search_result"))
                .andExpect(model().attributeExists("books"))
                .andExpect(model().attributeExists("page"))
                .andExpect(model().attribute("keyword", "java"))
                .andExpect(model().attribute("category", nullValue()));

        verify(bookSearchService).searchBooks(eq("java"), any(Pageable.class));
        verify(bookSearchService, never()).searchBooksByCategory(anyString(), any(Pageable.class));
    }

    @DisplayName("카테고리로 도서 검색 - 결과와 모델 검증")
    @Test
    void test_searchBooks_withCategory() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        List<BookDocument> books = List.of(createBook(3L, null));
        Page<BookDocument> page = new PageImpl<>(books, pageable, 1);

        when(bookSearchService.searchBooksByCategory(eq("programming"), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/books/search-es")
                        .param("category", "programming")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("book/search_result"))
                .andExpect(model().attributeExists("books"))
                .andExpect(model().attributeExists("page"))
                .andExpect(model().attribute("keyword", nullValue()))
                .andExpect(model().attribute("category", "programming"));

        verify(bookSearchService).searchBooksByCategory(eq("programming"), any(Pageable.class));
        verify(bookSearchService, never()).searchBooks(anyString(), any(Pageable.class));
    }

    @DisplayName("category 전용 검색 API 테스트")
    @Test
    void test_searchBooksByCategoryApi() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        List<BookDocument> books = List.of(createBook(5L, null));
        Page<BookDocument> page = new PageImpl<>(books, pageable, 1);

        when(bookSearchService.searchBooksByCategory(eq("fiction"), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/books/search-es/category")
                        .param("category", "fiction")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("book/search_result"))
                .andExpect(model().attributeExists("books"))
                .andExpect(model().attributeExists("page"))
                .andExpect(model().attribute("category", "fiction"));

        verify(bookSearchService).searchBooksByCategory(eq("fiction"), any(Pageable.class));
    }
}

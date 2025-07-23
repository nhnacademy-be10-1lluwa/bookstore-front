package com.nhnacademy.illuwa.search.service;

import com.nhnacademy.illuwa.search.client.BookSearchClient;
import com.nhnacademy.illuwa.search.dto.BookDocument;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookSearchServiceTest {
    @Mock
    private BookSearchClient bookSearchClient;

    @InjectMocks
    private BookSearchService bookSearchService;

    private BookDocument createBook(Long id, String thumbnailUrl) {
        return BookDocument.builder()
                .id(id)
                .title("Title " + id)
                .description("Description " + id)
                .author("Author " + id)
                .publisher("Publisher " + id)
                .isbn("ISBN" + id)
                .salePrice(BigDecimal.valueOf(1000 + id))
                .thumbnailUrl(thumbnailUrl)
                .publishedDate(LocalDate.of(2020, 1, 1).plusDays(id))
                .build();
    }

    @DisplayName("searchBooks 메서드: 썸네일 변환과 올바른 페이징 결과 반환 테스트")
    @Test
    void test_searchBooks() {
        Pageable pageable = PageRequest.of(0, 2, Sort.by(Sort.Direction.ASC, "id"));
        BookDocument book1 = createBook(1L, "http://storage.java21.net:8000/image1.png");
        BookDocument book2 = createBook(2L, null);

        Page<BookDocument> mockPage = new PageImpl<>(List.of(book1, book2), pageable, 2);

        when(bookSearchClient.searchBooks(eq("keyword"), eq(0), eq(2), eq("id,asc")))
                .thenReturn(mockPage);

        Page<BookDocument> resultPage = bookSearchService.searchBooks("keyword", pageable);

        assertEquals(2, resultPage.getTotalElements());
        assertEquals(2, resultPage.getContent().size());

        // 썸네일 URL 변환 확인
        assertEquals("https://book1lluwa.store/minio/image1.png", resultPage.getContent().get(0).getThumbnailUrl());
        assertNull(resultPage.getContent().get(1).getThumbnailUrl());

        verify(bookSearchClient, times(1))
                .searchBooks(eq("keyword"), eq(0), eq(2), eq("id,asc"));
    }

    @DisplayName("searchBooksByCategory 메서드: 썸네일 변환 및 페이징 반환 테스트")
    @Test
    void test_searchBooksByCategory() {
        Pageable pageable = PageRequest.of(1, 1, Sort.by(Sort.Direction.ASC, "title"));
        BookDocument book = createBook(3L, "http://storage.java21.net:8000/image3.png");

        Page<BookDocument> mockPage = new PageImpl<>(List.of(book), pageable, 5);

        when(bookSearchClient.searchBooks(eq("categoryA"), eq(1), eq(1), eq("title,asc")))
                .thenReturn(mockPage);

        Page<BookDocument> resultPage = bookSearchService.searchBooksByCategory("categoryA", pageable);

        assertEquals(5, resultPage.getTotalElements());
        assertEquals(1, resultPage.getContent().size());
        assertEquals("https://book1lluwa.store/minio/image3.png", resultPage.getContent().get(0).getThumbnailUrl());

        verify(bookSearchClient, times(1))
                .searchBooks(eq("categoryA"), eq(1), eq(1), eq("title,asc"));
    }
}

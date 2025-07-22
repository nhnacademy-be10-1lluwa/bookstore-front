package com.nhnacademy.illuwa.book.service;

import com.nhnacademy.illuwa.book.client.ProductServiceClient;
import com.nhnacademy.illuwa.book.dto.BestSellerResponse;
import com.nhnacademy.illuwa.book.dto.BookDetailResponse;
import com.nhnacademy.illuwa.category.dto.CategoryResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    ProductServiceClient productServiceClient;

    @InjectMocks
    BookService bookService;

    @Test
    @DisplayName("ISBN으로 도서 단건 조회 - 정상")
    void findBookByIsbn_success() {
        BookDetailResponse mock = new BookDetailResponse(1L, "title", "본문", "설명", "저자", "출판사", "2023-01-01", "isbn1",
                new BigDecimal("10000"), new BigDecimal("9000"), false, List.of("img1"), 5, "판매중");
        when(productServiceClient.findBookByIsbn("isbn1")).thenReturn(mock);

        BookDetailResponse result = bookService.findBookByIsbn("isbn1");

        assertThat(result).isEqualTo(mock);
        verify(productServiceClient).findBookByIsbn("isbn1");
    }

    @Test
    @DisplayName("ID로 도서 단건 조회 - 정상")
    void findBookById_success() {
        BookDetailResponse mock = new BookDetailResponse(2L, "title2", "본문", "설명", "저자", "출판사", "2023-01-02", "isbn2",
                new BigDecimal("10000"), new BigDecimal("9000"), false, List.of("img2"), 10, "판매중");
        when(productServiceClient.findBookById(2L)).thenReturn(mock);

        BookDetailResponse result = bookService.findBookById(2L);

        assertThat(result).isEqualTo(mock);
        verify(productServiceClient).findBookById(2L);
    }

    @Test
    @DisplayName("키워드로 도서 상세 조회 - 정상")
    void bookDetail_success() {
        BookDetailResponse mock = new BookDetailResponse(3L, "title3", "본문", "설명", "저자", "출판사", "2023-01-03", "isbn3",
                new BigDecimal("10000"), new BigDecimal("9000"), false, List.of("img3"), 15, "판매중");
        when(productServiceClient.getBookDetail("keyword")).thenReturn(mock);

        BookDetailResponse result = bookService.bookDetail("keyword");

        assertThat(result).isEqualTo(mock);
        verify(productServiceClient).getBookDetail("keyword");
    }

    @Test
    @DisplayName("전체 도서 목록 조회")
    void getAllBooks_success() {
        List<BookDetailResponse> list = Arrays.asList(
                new BookDetailResponse(), new BookDetailResponse()
        );
        when(productServiceClient.getRegisteredBook()).thenReturn(list);

        List<BookDetailResponse> result = bookService.getAllBooks();

        assertThat(result).containsExactlyElementsOf(list);
        verify(productServiceClient).getRegisteredBook();
    }

    @Test
    @DisplayName("페이징 도서 목록")
    void getPagedBooks_success() {
        List<BookDetailResponse> paged = Arrays.asList(
                new BookDetailResponse(), new BookDetailResponse()
        );
        PageImpl<BookDetailResponse> page = new PageImpl<>(paged);
        when(productServiceClient.getRegisteredBookPaged(0, 10, "id"))
                .thenReturn(page);

        Page<BookDetailResponse> result = bookService.getPagedBooks(0, 10, "id");

        assertThat(result.getContent()).isEqualTo(paged);
        verify(productServiceClient).getRegisteredBookPaged(0, 10, "id");
    }

    @Test
    @DisplayName("전체 카테고리 조회")
    void getAllCategory_success() {
        List<CategoryResponse> categories = Arrays.asList(
                new CategoryResponse(1L, null, "소설", Collections.emptyList()),
                new CategoryResponse(2L, 1L, "경제", Collections.emptyList())
        );
        when(productServiceClient.getAllCategories()).thenReturn(categories);

        List<CategoryResponse> result = bookService.getAllCategory();

        assertThat(result).containsExactlyElementsOf(categories);
        verify(productServiceClient).getAllCategories();
    }

    @Test
    @DisplayName("베스트셀러 목록 조회 (캐시 적용 대상)")
    void getBestSellers_success() {
        List<BestSellerResponse> bests = Arrays.asList(
                new BestSellerResponse(), new BestSellerResponse()
        );
        when(productServiceClient.getBestSeller()).thenReturn(bests);

        List<BestSellerResponse> result = bookService.getBestSellers();

        assertThat(result).isEqualTo(bests);
        verify(productServiceClient).getBestSeller();
    }

    @Test
    @DisplayName("ISBN으로 도서 조회 - 예외 발생")
    void findBookByIsbn_exception() {
        when(productServiceClient.findBookByIsbn("none"))
                .thenThrow(new RuntimeException("Not found"));

        assertThatThrownBy(() -> bookService.findBookByIsbn("none"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Not found");
    }

    @Test
    @DisplayName("도서 상세조회 결과 없음")
    void bookDetail_null() {
        when(productServiceClient.getBookDetail("badKeyword")).thenReturn(null);

        BookDetailResponse result = bookService.bookDetail("badKeyword");
        assertThat(result).isNull();
    }

}

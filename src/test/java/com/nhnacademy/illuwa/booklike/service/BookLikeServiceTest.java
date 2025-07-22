package com.nhnacademy.illuwa.booklike.service;

import com.nhnacademy.illuwa.book.dto.SimpleBookResponse;
import com.nhnacademy.illuwa.booklike.client.BookLikeServiceClient;
import com.nhnacademy.illuwa.common.dto.PageResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookLikeServiceTest {

    @Mock
    BookLikeServiceClient bookLikeServiceClient;

    @InjectMocks
    BookLikeService bookLikeService;

    @Test
    @DisplayName("좋아요 여부 확인 -> true")
    void testIsLikeByMe_True() {
        Long testBookId = 1L;

        when(bookLikeServiceClient.isLikedByMe(anyLong())).thenReturn(true);

        Boolean result = bookLikeService.isLikedByMe(testBookId);

        assertTrue(result);
        verify(bookLikeServiceClient, times(1)).isLikedByMe(anyLong());
    }

    @Test
    @DisplayName("좋아요 여부 -> false")
    void testIsLikeByMe_False() {
        Long testBookId = 1L;

        when(bookLikeServiceClient.isLikedByMe(anyLong())).thenReturn(false);

        Boolean result = bookLikeService.isLikedByMe(testBookId);

        assertFalse(result);
        verify(bookLikeServiceClient, times(1)).isLikedByMe(anyLong());
    }

    @Test
    @DisplayName("좋아요 클릭")
    void toggleBookLikesTest() {
        Long testBookId = 1L;

        doNothing().when(bookLikeServiceClient).toggleBookLikes(anyLong());

        assertDoesNotThrow(() -> bookLikeService.toggleBookLikes(testBookId));

        verify(bookLikeServiceClient, times(1)).toggleBookLikes(anyLong());
    }

    @Test
    @DisplayName("좋아요 도서 목록 조회")
    void getLikedBooksByMemberTest() {
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

        List<SimpleBookResponse> testBookList = Arrays.asList(testBook1, testBook2);

        PageResponse<SimpleBookResponse> pageResponse = new PageResponse<>(
                testBookList,
                1,
                testBookList.size(),
                page,
                size,
                true,
                true
        );

        when(bookLikeServiceClient.getLikedBooksByMember(anyInt(), anyInt())).thenReturn(pageResponse);

        PageResponse<SimpleBookResponse> result = bookLikeService.getLikedBooksByMember(page, size);

        assertEquals(pageResponse, result);
        assertEquals(testBookList.size(), result.content().size());
        assertEquals("Test Book 1", result.content().getFirst().getTitle());

        verify(bookLikeServiceClient, times(1)).getLikedBooksByMember(anyInt(), anyInt());
    }

}

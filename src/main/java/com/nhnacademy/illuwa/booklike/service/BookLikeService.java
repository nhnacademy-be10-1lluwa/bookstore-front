package com.nhnacademy.illuwa.booklike.service;

import com.nhnacademy.illuwa.booklike.client.BookLikeServiceClient;
import com.nhnacademy.illuwa.booklike.dto.BookLikeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookLikeService {
    private final BookLikeServiceClient bookLikeServiceClient;

    public BookLikeResponse toggleBookLikes(Long bookId){
        return bookLikeServiceClient.toggleBookLikes(bookId);
    }

    public Boolean isLikedByMe(Long bookId){
        return bookLikeServiceClient.isLikedByMe(bookId);
    }
}

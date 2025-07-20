package com.nhnacademy.illuwa.booklike.service;

import com.nhnacademy.illuwa.book.dto.BestSellerResponse;
import com.nhnacademy.illuwa.booklike.client.BookLikeServiceClient;
import com.nhnacademy.illuwa.common.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class BookLikeService {
    private final BookLikeServiceClient bookLikeServiceClient;

    public void toggleBookLikes(Long bookId){
        bookLikeServiceClient.toggleBookLikes(bookId);
    }

    public PageResponse<BestSellerResponse> getLikedBooksByMember(int page, int size){
        return bookLikeServiceClient.getLikedBooksByMember(page, size);
    }

    public Boolean isLikedByMe(Long bookId){
        return bookLikeServiceClient.isLikedByMe(bookId);
    }
}

package com.nhnacademy.illuwa.review.service;

import com.nhnacademy.illuwa.review.client.ReviewServiceClient;
import com.nhnacademy.illuwa.review.dto.ReviewLikeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@RequiredArgsConstructor
public class ReviewLikeService {
    private final ReviewServiceClient reviewServiceClient;

    public ReviewLikeResponse toggleLike(@PathVariable Long bookId, @PathVariable Long reviewId){

        return reviewServiceClient.toggleLike(bookId, reviewId);
    }
}

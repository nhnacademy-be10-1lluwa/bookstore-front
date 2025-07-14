package com.nhnacademy.illuwa.review.service;

import com.nhnacademy.illuwa.common.dto.PageResponse;
import com.nhnacademy.illuwa.review.client.ReviewServiceClient;
import com.nhnacademy.illuwa.review.dto.ReviewRequest;
import com.nhnacademy.illuwa.review.dto.ReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService{
    private final ReviewServiceClient reviewServiceClient;

    public void createReview(Long bookId, ReviewRequest request) throws Exception {
        reviewServiceClient.createReview(bookId, request);
    }

    public void updateReview(Long bookId, Long reviewId, ReviewRequest request) throws Exception {
        reviewServiceClient.updateReview(bookId, reviewId, request);
    }

    public ReviewResponse getReview(Long reviewId, Long memberId) {
        return reviewServiceClient.getReviewDetails(reviewId, memberId);
    }

    public PageResponse<ReviewResponse> getReviewPages(Long bookId, int page, int size) {
        Page<ReviewResponse> pagingData = reviewServiceClient.getReviewPages(bookId, page, size);
        return PageResponse.from(pagingData);
    }
}
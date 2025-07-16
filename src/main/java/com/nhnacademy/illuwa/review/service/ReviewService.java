package com.nhnacademy.illuwa.review.service;

import com.nhnacademy.illuwa.common.dto.PageResponse;
import com.nhnacademy.illuwa.review.client.ReviewServiceClient;
import com.nhnacademy.illuwa.review.dto.ReviewRequest;
import com.nhnacademy.illuwa.review.dto.ReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReviewService{
    private final ReviewServiceClient reviewServiceClient;

    public ReviewResponse createReview(Long bookId, ReviewRequest request) throws Exception {
        return reviewServiceClient.createReview(bookId, request);
    }

    public ReviewResponse updateReview(Long bookId, Long reviewId, ReviewRequest request) throws Exception {
        return reviewServiceClient.updateReview(bookId, reviewId, request);
    }

    public ReviewResponse getReview(Long bookId, Long reviewId) {
        return reviewServiceClient.getReviewDetails(bookId, reviewId);
    }

    public PageResponse<ReviewResponse> getReviewPages(Long bookId, int page, int size) {
        Page<ReviewResponse> pagingData = reviewServiceClient.getReviewPages(bookId, page, size);
        return PageResponse.from(pagingData);
    }

    public Map<Long, Boolean> areReviewsWritten(List<Long> bookIds){
        return reviewServiceClient.areReviewsWritten(bookIds);
    }

    public Map<Long, Long> getExistingReviewIdMap(List<Long> bookIds){
        return reviewServiceClient.getExistingReviewIdMap(bookIds);
    }
}
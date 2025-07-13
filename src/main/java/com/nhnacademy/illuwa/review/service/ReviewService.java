package com.nhnacademy.illuwa.review.service;

import com.nhnacademy.illuwa.review.client.ReviewServiceClient;
import com.nhnacademy.illuwa.review.dto.ReviewRequest;
import com.nhnacademy.illuwa.review.dto.ReviewResponse;
import com.nhnacademy.illuwa.member.client.MemberServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
//    public<ReviewResponse> getReviewPages(Long bookId,  pageable, Long memberId) {
//
//    }
//
}
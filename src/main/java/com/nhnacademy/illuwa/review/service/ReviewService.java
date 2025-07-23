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

    public void createReview(Long bookId, ReviewRequest request) throws Exception {
        reviewServiceClient.createReview(bookId, request);
    }

    public ReviewResponse updateReview(Long bookId, Long reviewId, ReviewRequest request) throws Exception {
        return reviewServiceClient.updateReview(bookId, reviewId, request);
    }

    public ReviewResponse getReview(Long bookId, Long reviewId) {
        return reviewServiceClient.getReviewDetails(bookId, reviewId);
    }

    public PageResponse<ReviewResponse> getReviewPages(Long bookId, int page, int size) {
        Page<ReviewResponse> pagingData = reviewServiceClient.getReviewPages(bookId, page, size);

        Page<ReviewResponse> convertedData = pagingData.map(review -> {
            List<String> convertedUrls = review.getReviewImageUrls().stream()
                    .map(this::convertMinioUrl)
                    .toList();

            return new ReviewResponse(
                    review.getReviewId(),
                    review.getReviewTitle(),
                    review.getReviewContent(),
                    review.getReviewRating(),
                    review.getReviewDate(),
                    review.getBookId(),
                    review.getMemberId(),
                    convertedUrls
            );
        });

        return PageResponse.from(convertedData);
    }

    public PageResponse<ReviewResponse> getMemberReviewPages(int page, int size) {
        return reviewServiceClient.getMemberReviewPages(page, size);
    }

    public Map<Long, Long> getExistingReviewIdMap(List<Long> bookIds){
        return reviewServiceClient.getExistingReviewIdMap(bookIds);
    }

    public Map<Long, String> getBookTitlesFromReviewIds(List<Long> reviewIds){
        return reviewServiceClient.getBookTitleMapFromReviewIds(reviewIds);
    }

    private String convertMinioUrl(String originalUrl) {
        if (originalUrl == null) {
            return null;
        }
        return originalUrl.replace(
                "http://storage.java21.net:8000/",
                "https://book1lluwa.store/minio/"
        );
    }
}
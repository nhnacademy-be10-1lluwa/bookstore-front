package com.nhnacademy.illuwa.review.client;

import com.nhnacademy.illuwa.common.dto.PageResponse;
import com.nhnacademy.illuwa.config.FeignClientConfig;
import com.nhnacademy.illuwa.review.dto.*;
import jakarta.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@FeignClient(name = "product-service", url = "${api.base-url}", contextId = "ProductClientForReview", configuration = FeignClientConfig.class)
public interface ReviewServiceClient {


    @PostMapping(value = "/api/reviews", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ReviewResponse createReview(@RequestParam(name = "book-id") long bookId,
                                @ModelAttribute @Valid ReviewRequest request) throws Exception;

    @PostMapping(value = "/api/reviews/{review-id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ReviewResponse updateReview(@RequestParam(name = "book-id") long bookId,
                                @PathVariable(name = "review-id") long reviewId,
                                @ModelAttribute @Valid ReviewRequest request) throws Exception;

    @GetMapping(value = "/api/reviews/{review-id}")
    ReviewResponse getReviewDetails(@RequestParam(name = "book-id") long bookId, @PathVariable(name = "review-id") long reviewId);

    @GetMapping(value = "/api/public/reviews")
    Page<ReviewResponse> getReviewPages(@RequestParam(name = "book-id") long bookId,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "5") int size);

    @GetMapping(value = "/api/reviews")
    PageResponse<ReviewResponse> getMemberReviewPages(@RequestParam("page") int page,
                                                      @RequestParam("size") int size);

    @PostMapping("/api/reviews/{review-id}/likes")
    ReviewLikeResponse toggleLike(@PathVariable(name = "review-id") long reviewId);

    @GetMapping("/api/public/reviews/likes")
    Map<Long, Long> getLikeCountsFromReviews(@RequestParam(name = "review-ids") List<Long> reviewIds);

    @GetMapping("/api/reviews/likes/status")
    List<Long> getMyLikedReviews(@RequestParam(name = "review-ids") List<Long> reviewIds);

    @PostMapping("/api/reviews/check")
    Map<Long, Long> getExistingReviewIdMap(@RequestBody List<Long> bookIds);

    @GetMapping("/api/reviews/book-title")
    Map<Long, String> getBookTitleMapFromReviewIds(@RequestParam("review-ids") Collection<Long> reviewIds);
}

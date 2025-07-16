package com.nhnacademy.illuwa.review.client;


import com.nhnacademy.illuwa.config.FeignClientConfig;
import com.nhnacademy.illuwa.review.dto.*;
import jakarta.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "product-service", url = "${api.base-url}", contextId = "ProductClientForReview", configuration = FeignClientConfig.class)
public interface ReviewServiceClient {
    @PostMapping(value = "/api/book-reviews/{bookId}/reviews", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ReviewResponse createReview(@PathVariable long bookId,
                                @ModelAttribute @Valid ReviewRequest request) throws Exception;

    @PostMapping(value = "/api/book-reviews/{bookId}/reviews/{reviewId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ReviewResponse updateReview(@PathVariable long bookId,
                                @PathVariable long reviewId,
                                @ModelAttribute @Valid ReviewRequest request) throws Exception;

    @GetMapping(value = "/api/book-reviews/{bookId}/reviews/{reviewId}")
    ReviewResponse getReviewDetails(@PathVariable long bookId, @PathVariable long reviewId);

    @GetMapping(value = "/api/book-reviews/{bookId}/reviews")
    Page<ReviewResponse> getReviewPages(@PathVariable long bookId,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "5") int size);

    @PostMapping("/api/book-reviews/{bookId}/reviews/{reviewId}/likes")
    ReviewLikeResponse toggleLike(@PathVariable long bookId, @PathVariable long reviewId);

    @PostMapping("/api/book-reviews/{bookId}/reviews/{reviewId}/comments")
    CommentResponse createComment(@PathVariable long bookId,
                                  @PathVariable long reviewId,
                                  @RequestBody @Valid CommentRequest request);

    @GetMapping("/api/book-reviews/{bookId}/reviews/{reviewId}/comments")
    List<CommentResponse> getCommentList(@PathVariable long bookId, @PathVariable long reviewId);

    @PostMapping("/api/book-reviews/{bookId}/reviews/{reviewId}/comments/{commentId}")
    CommentResponse updateComment(@PathVariable long bookId,
                                  @PathVariable long reviewId,
                                  @PathVariable long commentId,
                                  @RequestBody @Valid CommentRequest request);

    @PostMapping("/api/book-reviews/reviews/check-batch")
    Map<Long, Boolean> areReviewsWritten(@RequestBody List<Long> bookIds);
}

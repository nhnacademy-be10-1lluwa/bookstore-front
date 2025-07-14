package com.nhnacademy.illuwa.review.client;


import com.nhnacademy.illuwa.config.FeignClientConfig;
import com.nhnacademy.illuwa.review.dto.*;
import jakarta.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "product-service", url = "${api.base-url}", contextId = "ProductClientForReview", configuration = FeignClientConfig.class)
public interface ReviewServiceClient {
    @PostMapping(value = "/api/books/{bookId}/reviews", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ReviewResponse createReview(@PathVariable long bookId,
                                @ModelAttribute @Valid ReviewRequest request) throws Exception;

    @PostMapping(value = "/api/books/{bookId}/reviews/{reviewId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ReviewResponse updateReview(@PathVariable long bookId,
                                @PathVariable long reviewId,
                                @ModelAttribute @Valid ReviewRequest request) throws Exception;

    @GetMapping(value = "/api/books/{bookId}/reviews/{reviewId}")
    ReviewResponse getReviewDetails(@PathVariable long bookId, @PathVariable long reviewId);

    @GetMapping(value = "/api/books/{bookId}/reviews")
    Page<ReviewResponse> getReviewPages(@PathVariable Long bookId,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "5") int size);

    @PostMapping("/api/books/{bookId}/reviews/{reviewId}/likes")
    ReviewLikeResponse toggleLike(@PathVariable Long bookId, @PathVariable Long reviewId);

    @PostMapping("/api/books/{bookId}/reviews/{reviewId}/comments")
    CommentResponse createComment(@PathVariable Long bookId,
                                  @PathVariable Long reviewId,
                                  @RequestBody @Valid CommentRequest request);

    @GetMapping("/api/books/{bookId}/reviews/{reviewId}/comments")
    List<CommentResponse> getCommentList(@PathVariable Long bookId, @PathVariable Long reviewId);

    @PostMapping("/api/books/{bookId}/reviews/{reviewId}/comments/{commentId}")
    CommentResponse updateComment(@PathVariable Long bookId,
                                  @PathVariable Long reviewId,
                                  @PathVariable Long commentId,
                                  @RequestBody @Valid CommentRequest request);
}

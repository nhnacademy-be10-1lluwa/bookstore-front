package com.nhnacademy.illuwa.review.client;


import com.nhnacademy.illuwa.config.FeignClientConfig;
import com.nhnacademy.illuwa.review.dto.*;
import jakarta.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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

//    @GetMapping(value = "/reviews")
//    List<ReviewResponse> getReviewPages(@PathVariable Long bookId,
//

//    @PostMapping("/reviews/{reviewId}/likes")
//    ResponseEntity<ReviewLikeResponse> toggleLike(Long memberId, @PathVariable Long bookId, @PathVariable Long reviewId);
//
//    @PostMapping("/reviews/{reviewId}/comments")
//    ResponseEntity<CommentResponse> createComment(Long memberId,
//                                                  @PathVariable Long bookId,
//                                                  @PathVariable Long reviewId,
//                                                  @RequestBody @Valid CommentRequest request);
//
//    @GetMapping("/reviews/{reviewId}/comments")
//    ResponseEntity<List<CommentResponse>> getCommentList(Long memberId, @PathVariable Long bookId, @PathVariable Long reviewId);
//
//    @PatchMapping("/reviews/{reviewId}/comments/{commentId}")
//    ResponseEntity<CommentResponse> updateComment(Long memberId,
//                                                  @PathVariable Long bookId,
//                                                  @PathVariable Long reviewId,
//                                                  @PathVariable Long commentId,
//                                                  @RequestBody @Valid CommentRequest request);
}

package com.nhnacademy.illuwa.review.client;

import com.nhnacademy.illuwa.config.FeignMultipartSupportConfig;
import com.nhnacademy.illuwa.review.dto.*;
import jakarta.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(name = "product-service", url = "${api.base-url}", contextId = "review-service", configuration = FeignMultipartSupportConfig.class)
public interface ReviewServiceClient {
    @PostMapping(value = "/books/{bookId}/reviews", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ReviewResponse createReview(@RequestPart("review") @Valid ReviewRequest request,
                                @RequestPart(name = "images", required = false) List<MultipartFile> images) throws Exception;

    @PatchMapping(value = "/books/{bookId}/reviews/{reviewId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ReviewResponse updateReview(@PathVariable Long bookId,
                                @PathVariable Long reviewId,
                                @RequestHeader("X-USER-ID") Long memberId,
                                @RequestPart("review") @Valid ReviewRequest request,
                                @RequestPart(name = "images", required = false) List<MultipartFile> images) throws Exception;
    @GetMapping(value = "/books/{bookId}/reviews/{reviewId}")
    ReviewResponse getReviewDetails(@PathVariable Long bookId,
                                    @PathVariable Long reviewId,
                                    @RequestHeader("X-USER-ID") Long memberId);

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

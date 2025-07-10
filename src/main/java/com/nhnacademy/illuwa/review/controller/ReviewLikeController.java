package com.nhnacademy.illuwa.review.controller;

import com.nhnacademy.illuwa.review.client.ReviewServiceClient;
import com.nhnacademy.illuwa.review.dto.ReviewLikeResponse;
import com.nhnacademy.illuwa.member.client.MemberServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books/{bookId}/reviews/{reviewId}/likes")
public class ReviewLikeController {
//    private final ReviewServiceClient reviewServiceClient;
//    private final MemberServiceClient memberServiceClient;
//    @PostMapping
//    public ResponseEntity<ReviewLikeResponse> toggleLike(@PathVariable Long bookId,
//                                                         @PathVariable Long reviewId) {
//
//        Long memberId = memberServiceClient.getMember().getMemberId();
//        return reviewServiceClient.toggleLike(memberId, bookId, reviewId);
//    }
}
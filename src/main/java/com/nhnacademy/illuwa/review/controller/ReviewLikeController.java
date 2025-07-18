package com.nhnacademy.illuwa.review.controller;

import com.nhnacademy.illuwa.review.dto.ReviewLikeResponse;
import com.nhnacademy.illuwa.review.service.ReviewLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ReviewLikeController {
    private final ReviewLikeService reviewLikeService;

    @PostMapping("/reviews/{review-id}/likes")
    public ReviewLikeResponse toggleLike(@PathVariable(name = "review-id") Long reviewId) {

        return reviewLikeService.toggleLike(reviewId);
    }

    @GetMapping("/reviews/likes")
    public Map<Long, Long> getLikeCountsFromReviews(List<Long> reviewIds) {
        return reviewLikeService.getLikeCountsFromReviews(reviewIds);
    }

    @GetMapping("/reviews/likes/status")
    public List<Long> getMyLikedReviews(List<Long> reviewIds) {
        return reviewLikeService.getMyLikedReviews(reviewIds);
    }
}
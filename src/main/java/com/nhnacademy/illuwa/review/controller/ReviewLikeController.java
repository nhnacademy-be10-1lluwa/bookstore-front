package com.nhnacademy.illuwa.review.controller;

import com.nhnacademy.illuwa.review.dto.ReviewLikeResponse;
import com.nhnacademy.illuwa.review.service.ReviewLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/books/{bookId}/reviews/{reviewId}/likes")
public class ReviewLikeController {
    private final ReviewLikeService reviewLikeService;

    @PostMapping
    public ReviewLikeResponse toggleLike(@PathVariable Long bookId, @PathVariable Long reviewId) {

        return reviewLikeService.toggleLike(bookId, reviewId);
    }
}
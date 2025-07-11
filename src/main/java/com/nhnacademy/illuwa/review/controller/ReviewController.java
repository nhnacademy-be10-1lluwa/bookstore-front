package com.nhnacademy.illuwa.review.controller;

import com.nhnacademy.illuwa.review.dto.ReviewRequest;
import com.nhnacademy.illuwa.review.dto.ReviewResponse;
import com.nhnacademy.illuwa.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/books/{bookId}/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/create")
    public String reviewCreateForm(Model model,
                                   @PathVariable Long bookId) {

        model.addAttribute("isEdit", false);
        model.addAttribute("bookId", bookId);
        model.addAttribute("memberId", reviewService.getMemberId());

        return "review/reviewForm";
    }

    @PostMapping
    public String reviewCreate(@ModelAttribute ReviewRequest reviewRequest,
                               @RequestParam(value = "images", required = false) List<MultipartFile> images) throws Exception {
        reviewService.createReview(reviewRequest, images);

        //return "redirect:/order_list";
        return "redirect:/";
    }

    @GetMapping("/{reviewId}/edit")
    public String reviewEditForm(Model model,
                                 @PathVariable Long bookId,
                                 @PathVariable Long reviewId,
                                 @RequestHeader("X-USER-ID") Long memberId) {

        ReviewResponse reviewData = reviewService.getReview(bookId, reviewId, memberId);

        model.addAttribute("isEdit", true);
        model.addAttribute("bookId", bookId);
        model.addAttribute("memberId", memberId);
        model.addAttribute("reviewId", reviewId);
        model.addAttribute("reviewData", reviewData);

        return "review/reviewForm";
    }

    @PostMapping("/{reviewId}")
    public String reviewUpdate(@PathVariable Long bookId,
                               @PathVariable Long reviewId,
                               @RequestHeader("X-USER-ID") Long memberId,
                               @ModelAttribute ReviewRequest reviewRequest,
                               @RequestParam(value = "images", required = false) List<MultipartFile> images) throws Exception {
        bookId=18L;
        reviewService.updateReview(bookId, reviewId, memberId, reviewRequest, images);

        //return "redirect:/order_list";
        return "redirect:/";
    }

//    @GetMapping
//    public String getReviewPages(Model model, @PathVariable Long bookId, @RequestParam int page, ){
//
//    }
//
}
package com.nhnacademy.illuwa.review.controller;

import com.nhnacademy.illuwa.review.dto.ReviewRequest;
import com.nhnacademy.illuwa.review.dto.ReviewResponse;
import com.nhnacademy.illuwa.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/books/{bookId}/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping
    public String reviewCreateForm(Model model, @PathVariable long bookId) {

        model.addAttribute("isEdit", false);
        model.addAttribute("bookId", bookId);

        return "review/reviewForm";
    }

    @PostMapping
    public String reviewCreate(@PathVariable long bookId,
                               @ModelAttribute ReviewRequest request,
                               @RequestParam(value = "images", required = false) List<MultipartFile> images) throws Exception {
        log.info("{} // {} // {} // {}", bookId, request.getReviewTitle(), request.getReviewContent(), request.getReviewRating());
        reviewService.createReview(bookId, request, images);

        //return "redirect:/order_list";
        return "redirect:/mypage";
    }

    @GetMapping("/{reviewId}")
    public String reviewEditForm(Model model,
                                 @PathVariable long bookId,
                                 @PathVariable long reviewId) {

        ReviewResponse reviewData = reviewService.getReview(bookId, reviewId);

        model.addAttribute("isEdit", true);
        model.addAttribute("bookId", bookId);
        model.addAttribute("reviewId", reviewId);
        model.addAttribute("reviewData", reviewData);

        return "review/reviewForm";
    }

    @PostMapping("/{reviewId}")
    public String reviewUpdate(@PathVariable long bookId,
                               @PathVariable long reviewId,
                               @ModelAttribute ReviewRequest request,
                               @RequestParam(value = "images", required = false) List<MultipartFile> images) throws Exception {
        log.info("{} // {} // {} // {}", bookId, request.getReviewTitle(), request.getReviewContent(), request.getReviewRating());
        reviewService.updateReview(bookId, reviewId, request, images);

        //return "redirect:/order_list";
        return "redirect:/mypage";
    }

//    @GetMapping
//    public String getReviewPages(Model model, @PathVariable Long bookId, @RequestParam int page, ){
//
//    }
//
}
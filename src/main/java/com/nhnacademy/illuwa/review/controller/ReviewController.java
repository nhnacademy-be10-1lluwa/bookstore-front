package com.nhnacademy.illuwa.review.controller;

import com.nhnacademy.illuwa.common.dto.PageResponse;
import com.nhnacademy.illuwa.review.dto.ReviewRequest;
import com.nhnacademy.illuwa.review.dto.ReviewResponse;
import com.nhnacademy.illuwa.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/books/{bookId}/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping
    public String reviewCreateForm(Model model, @PathVariable long bookId) {

        model.addAttribute("isEdit", false);
        model.addAttribute("bookId", bookId);

        return "review/reviewForm";
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String reviewCreate(@PathVariable long bookId,
                               @ModelAttribute @Valid ReviewRequest request) throws Exception {

        reviewService.createReview(bookId, request);

        return "redirect:/order_list";
    }

    @GetMapping(value = "/{reviewId}")
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

    @PostMapping(value = "/{reviewId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String reviewUpdate(@PathVariable long bookId,
                               @PathVariable long reviewId,
                               @ModelAttribute @Valid ReviewRequest request) throws Exception {

        reviewService.updateReview(bookId, reviewId, request);

        return "redirect:/order_list";
    }

    @GetMapping
    @ResponseBody
    public PageResponse<ReviewResponse> getReviewPages(@PathVariable Long bookId,
                                                       @RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "5") int size) {

        return reviewService.getReviewPages(bookId, page, size);
    }
}
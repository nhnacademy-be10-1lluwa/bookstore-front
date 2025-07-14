package com.nhnacademy.illuwa.review.controller;

import com.nhnacademy.illuwa.review.dto.ReviewRequest;
import com.nhnacademy.illuwa.review.dto.ReviewResponse;
import com.nhnacademy.illuwa.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
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
        //return "redirect:/mypage";
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
        //return "redirect:/mypage";
    }

//    @GetMapping
//    public String getReviewPages(Model model, @PathVariable Long bookId, @RequestParam int page, ){
//
//    }
//
}
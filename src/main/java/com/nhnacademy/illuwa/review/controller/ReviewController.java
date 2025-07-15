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

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/books/{bookId}/reviews/write")
    public String reviewCreateForm(Model model, @PathVariable long bookId) {

        model.addAttribute("isEdit", false);
        model.addAttribute("bookId", bookId);

        return "review/reviewForm";
    }

    @PostMapping(value = "/books/{bookId}/reviews", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String reviewCreate(@PathVariable long bookId,
                               @ModelAttribute @Valid ReviewRequest request){
        try{
            reviewService.createReview(bookId, request);

            return "redirect:/order-list";
        } catch (Exception e){
            return "redirect:/";
        }

    }

    @GetMapping(value = "/books/{bookId}/reviews/{reviewId}/edit")
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

    @PostMapping(value = "/books/{bookId}/reviews/{reviewId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String reviewUpdate(@PathVariable long bookId,
                               @PathVariable long reviewId,
                               @ModelAttribute @Valid ReviewRequest request){
        try{
            reviewService.updateReview(bookId, reviewId, request);

            return "redirect:/order-list";
        } catch (Exception e){
            return "redirect:/";
        }
    }

    @GetMapping(value = "/books/{bookId}/reviews")
    @ResponseBody
    public PageResponse<ReviewResponse> getReviewPages(@PathVariable Long bookId,
                                                       @RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "5") int size) {

        return reviewService.getReviewPages(bookId, page, size);
    }

    @PostMapping("/books/reviews/check-batch")
    Map<Long, Boolean> areReviewsWritten(@RequestBody List<Long> bookIds){
        return reviewService.areReviewsWritten(bookIds);
    }
}
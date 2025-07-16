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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    // 리뷰 작성 폼
    @GetMapping("/books/{bookId}/reviews/new")
    public String showCreateForm(Model model,
                                 @PathVariable long bookId,
                                 @RequestParam Long orderId) {
        model.addAttribute("mode", "new");
        model.addAttribute("bookId", bookId);
        model.addAttribute("orderId", orderId);
        model.addAttribute("review", null);
        return "review/review_form";
    }

    // 리뷰 상세 보기
    @GetMapping("/books/{bookId}/reviews/{reviewId}")
    public String showDetail(@PathVariable long bookId,
                             @PathVariable long reviewId,
                             @RequestParam Long orderId,
                             Model model) {
        ReviewResponse review = reviewService.getReview(bookId, reviewId);
        model.addAttribute("mode", "view");
        model.addAttribute("bookId", bookId);
        model.addAttribute("reviewId", reviewId);
        model.addAttribute("orderId", orderId);
        model.addAttribute("review", review);
        return "review/review_form";
    }

    // 리뷰 수정 폼
    @GetMapping("/books/{bookId}/reviews/{reviewId}/edit")
    public String showEditForm(@PathVariable long bookId,
                               @PathVariable long reviewId,
                               @RequestParam Long orderId,
                               Model model) {
        ReviewResponse review = reviewService.getReview(bookId, reviewId);
        model.addAttribute("mode", "edit");
        model.addAttribute("bookId", bookId);
        model.addAttribute("reviewId", reviewId);
        model.addAttribute("orderId", orderId);
        model.addAttribute("review", review);
        return "review/review_form";
    }

    // 리뷰 등록/수정 처리
    @PostMapping(value = "/books/{bookId}/reviews/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String saveReview(@RequestParam("mode") String mode,
                             @Valid @ModelAttribute ReviewRequest request,
                             @PathVariable long bookId,
                             @RequestParam(value = "reviewId", required = false) Long reviewId,
                             @RequestParam("orderId") Long orderId,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             Model model) {

        model.addAttribute("mode", mode);
        model.addAttribute("bookId", bookId);
        model.addAttribute("orderId", orderId);
        model.addAttribute("review", request);
        if (reviewId != null) model.addAttribute("reviewId", reviewId);

        if (bindingResult.hasErrors()) {
            return "review/review_form";
        }

        try {
            if ("edit".equals(mode)) {
                if (reviewId == null) throw new IllegalArgumentException("리뷰 ID가 없습니다.");
                reviewService.updateReview(bookId, reviewId, request);
                redirectAttributes.addFlashAttribute("message", "리뷰가 성공적으로 수정되었어요! ✨");
            } else {
                reviewService.createReview(bookId, request);
                redirectAttributes.addFlashAttribute("message", "리뷰가 성공적으로 등록되었어요! 🎉");
            }
        } catch (Exception e) {
            return "review/review_form";
        }

        return "redirect:/order-detail/" + orderId;
    }

    // 리뷰 목록 조회 (페이지)
    @GetMapping("/books/{bookId}/reviews")
    @ResponseBody
    public PageResponse<ReviewResponse> getReviewPages(@PathVariable Long bookId,
                                                       @RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "5") int size) {
        return reviewService.getReviewPages(bookId, page, size);
    }

    // 리뷰 존재 여부 확인 (배치 체크용)
    @PostMapping("/books/reviews/check-batch")
    @ResponseBody
    public Map<Long, Boolean> areReviewsWritten(@RequestBody List<Long> bookIds) {
        return reviewService.areReviewsWritten(bookIds);
    }
}
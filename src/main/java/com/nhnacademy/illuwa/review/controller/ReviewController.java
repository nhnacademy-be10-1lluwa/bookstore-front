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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    // 리뷰 작성 폼
    @GetMapping("/reviews/new")
    public String showCreateForm(Model model,
                                 @RequestParam(name = "book-id") long bookId,
                                 @RequestParam(name = "order-id", required = false) Long orderId) {
        model.addAttribute("activeMenu", model.getAttribute("activeMenu"));
        model.addAttribute("mode", "new");
        model.addAttribute("bookId", bookId);
        model.addAttribute("orderId", orderId);
        model.addAttribute("review", null);
        return "review/review_form";
    }

    // 리뷰 상세 보기
    @GetMapping("/reviews/{review-id}")
    public String showDetail(@RequestParam(name = "book-id") long bookId,
                             @PathVariable(name = "review-id") long reviewId,
                             @RequestParam(name = "order-id", required = false) Long orderId,
                             Model model) {
        ReviewResponse review = reviewService.getReview(bookId, reviewId);
        if (review == null) {
            throw new IllegalArgumentException("해당 리뷰를 찾을 수 없습니다.");
        }

        model.addAttribute("mode", "view");
        model.addAttribute("bookId", bookId);
        model.addAttribute("reviewId", reviewId);
        model.addAttribute("orderId", orderId);
        model.addAttribute("review", review);
        return "review/review_form";
    }

    // 리뷰 수정 폼
    @GetMapping("/reviews/{review-id}/edit")
    public String showEditForm(@RequestParam(name = "book-id") long bookId,
                               @PathVariable(name = "review-id") long reviewId,
                               @RequestParam(name = "order-id", required = false) Long orderId,
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
    @PostMapping(value = "/reviews/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String saveReview(@RequestParam(name = "mode") String mode,
                             @Valid @ModelAttribute ReviewRequest request,
                             @RequestParam(name = "book-id") long bookId,
                             @RequestParam(name = "review-id", required = false) Long reviewId,
                             @RequestParam(name = "order-id", required = false) Long orderId,
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
                ReviewResponse updated = reviewService.updateReview(bookId, reviewId, request);
                redirectAttributes.addFlashAttribute("message", "리뷰가 성공적으로 수정되었어요! ✨");
                return "redirect:/reviews/" + updated.getReviewId() + "?book-id=" + bookId +
                        (orderId != null ? "&order-id=" + orderId : "");
            } else {
                reviewService.createReview(bookId, request);
                redirectAttributes.addFlashAttribute("message", "리뷰가 성공적으로 등록되었어요! 🎉");
                return "redirect:/orders/" + orderId;
            }
        } catch (Exception e) {
            return "review/review_form";
        }
    }

    // 리뷰 목록 조회 (페이지)
    @GetMapping("/public/reviews")
    @ResponseBody
    public PageResponse<ReviewResponse> getReviewPages(@RequestParam(name = "book-id") long bookId,
                                                       @RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "5") int size) {
        return reviewService.getReviewPages(bookId, page, size);
    }

    // 내가 쓴 리뷰목록 조회 (페이지)
    @GetMapping("/review-history")
    public String getReviewHistory(Model model,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "5") int size) {
        PageResponse<ReviewResponse> reviewPage = reviewService.getMemberReviewPages(page, size);

        List<Long> reviewIds = new ArrayList<>();
        for(ReviewResponse review: reviewPage.content()){
            reviewIds.add(review.getReviewId());
        }
        Map<Long, String> titleList = reviewService.getBookTitlesFromReviewIds(reviewIds);

        model.addAttribute("reviewList", reviewPage.content());
        model.addAttribute("titleList", titleList);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", reviewPage.size());
        model.addAttribute("totalPages", reviewPage.totalPages());
        model.addAttribute("lastPageIndex", Math.max(0, reviewPage.totalPages() - 1));
        model.addAttribute("activeMenu", "review-history");
        return "mypage/section/review_history";
    }
}
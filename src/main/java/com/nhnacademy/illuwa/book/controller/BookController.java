package com.nhnacademy.illuwa.book.controller;

import com.nhnacademy.illuwa.auth.utils.JwtCookieUtil;
import com.nhnacademy.illuwa.book.dto.BookDetailResponse;
import com.nhnacademy.illuwa.book.service.BookService;
import com.nhnacademy.illuwa.booklike.service.BookLikeService;
import com.nhnacademy.illuwa.common.dto.PageResponse;
import com.nhnacademy.illuwa.member.service.MemberService;
import com.nhnacademy.illuwa.review.dto.ReviewResponse;
import com.nhnacademy.illuwa.review.service.ReviewLikeService;
import com.nhnacademy.illuwa.review.service.ReviewService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    private final ReviewService reviewService;
    private final ReviewLikeService reviewLikeService;
    private final MemberService memberService;
    private final BookLikeService bookLikeService;

    //도서 상세페이지
    @GetMapping("/books/{id}")
    public String bookDetailBySearch(@PathVariable("id") Long bookId, Model model, HttpServletRequest request){
        boolean isLoginUser = JwtCookieUtil.checkAccessToken(request);

        BookDetailResponse bookDetail = bookService.findBookById(bookId);

        PageResponse<ReviewResponse> reviewPage = reviewService.getReviewPages(bookId, 0, 5);

        List<Long> reviewMemberIds = Optional.ofNullable(reviewPage.content()).orElse(Collections.emptyList()).stream().map(ReviewResponse::getMemberId).toList();
        Map<Long, String> reviewerNameMap = memberService.getNamesFromIdList(reviewMemberIds);

        List<Long> reviewIds = Optional.ofNullable(reviewPage.content()).orElse(Collections.emptyList()).stream().map(ReviewResponse::getReviewId).toList();
        Map<Long, Long> likeCountMap = reviewLikeService.getLikeCountsFromReviews(reviewIds);

        model.addAttribute("book", bookDetail);
        model.addAttribute("reviewContent", reviewPage.content());
        model.addAttribute("reviewPage", reviewPage);
        model.addAttribute("reviewerNameMap", reviewerNameMap);
        model.addAttribute("likeCountMap", likeCountMap);
        model.addAttribute("isLoggedIn", isLoginUser);

        if(isLoginUser) {
            model.addAttribute("isMyLikedBook", bookLikeService.isLikedByMe(bookId));
            List<Long> myLikedReviewIds = reviewLikeService.getMyLikedReviews(reviewIds);
            model.addAttribute("myLikedReviewIds", myLikedReviewIds);
        }

        return "book/detail";
    }

    // isbn 기준으로 도서상세 가져오기
    @GetMapping("/books/isbn/{isbn}")
    public String bookDetail(@PathVariable("isbn") String isbn, Model model, HttpServletRequest request) {
        boolean isLoginUser = JwtCookieUtil.checkAccessToken(request);

        BookDetailResponse bookDetail = bookService.findBookByIsbn(isbn);
        Long bookId = bookDetail.getId();

        PageResponse<ReviewResponse> reviewPage = reviewService.getReviewPages(bookId, 0, 5);

        List<Long> reviewMemberIds = Optional.ofNullable(reviewPage.content()).orElse(Collections.emptyList()).stream().map(ReviewResponse::getMemberId).toList();
        Map<Long, String> reviewerNameMap = memberService.getNamesFromIdList(reviewMemberIds);

        List<Long> reviewIds = Optional.ofNullable(reviewPage.content()).orElse(Collections.emptyList()).stream().map(ReviewResponse::getReviewId).toList();
        Map<Long, Long> likeCountMap = reviewLikeService.getLikeCountsFromReviews(reviewIds);

        model.addAttribute("book", bookDetail);
        model.addAttribute("reviewContent", reviewPage.content());
        model.addAttribute("reviewPage", reviewPage);
        model.addAttribute("reviewerNameMap", reviewerNameMap);
        model.addAttribute("likeCountMap", likeCountMap);
        model.addAttribute("isLoggedIn", isLoginUser);

        if(isLoginUser) {
            model.addAttribute("isMyLikedBook", bookLikeService.isLikedByMe(bookId));
            List<Long> myLikedReviewIds = reviewLikeService.getMyLikedReviews(reviewIds);
            model.addAttribute("myLikedReviewIds", myLikedReviewIds);
        }

        return "book/detail";
    }
}
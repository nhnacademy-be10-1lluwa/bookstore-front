package com.nhnacademy.illuwa.book.controller;

import com.nhnacademy.illuwa.auth.utils.JwtCookieUtil;
import com.nhnacademy.illuwa.book.dto.BookDetailResponse;
import com.nhnacademy.illuwa.book.service.BookService;
import com.nhnacademy.illuwa.common.dto.PageResponse;
import com.nhnacademy.illuwa.member.service.MemberService;
import com.nhnacademy.illuwa.review.dto.CommentResponse;
import com.nhnacademy.illuwa.review.dto.ReviewResponse;
import com.nhnacademy.illuwa.review.service.CommentService;
import com.nhnacademy.illuwa.review.service.ReviewLikeService;
import com.nhnacademy.illuwa.review.service.ReviewService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    private final ReviewService reviewService;
    private final ReviewLikeService reviewLikeService;
    private final CommentService commentService;
    private final MemberService memberService;

//    @GetMapping("/user/book-info/{isbn}")
//    public String bookInfo(@PathVariable String isbn, Model model) {
//        return "book/book_info";
//    }

    @GetMapping("/books/{isbn}")
    public String bookDetail(@PathVariable("isbn") String isbn, Model model, HttpServletRequest request) {
        boolean isLoginUser = JwtCookieUtil.checkAccessToken(request);

        BookDetailResponse bookDetail = bookService.findBookByIsbn(isbn);

        Long bookId = bookDetail.getId();
        PageResponse<ReviewResponse> reviewPage = reviewService.getReviewPages(bookId, 0, 5);

        List<Long> memberIds = reviewPage.content().stream().map(ReviewResponse::getMemberId).toList();
        Map<Long,String> nameMap = memberService.getNamesFromIdList(memberIds);

        List<Long> reviewIds = reviewPage.content().stream().map(ReviewResponse::getReviewId).toList();
        Map<Long, Long> likeCountMap = reviewLikeService.getLikeCountsFromReviews(reviewIds);

        model.addAttribute("book", bookDetail);
        model.addAttribute("reviewContent", reviewPage.content());
        model.addAttribute("reviewPage", reviewPage);
        model.addAttribute("nameMap", nameMap);
        model.addAttribute("likeCountMap", likeCountMap);

        if(isLoginUser) {
            List<Long> myLikedReviewIds = reviewLikeService.getMyLikedReviews(reviewIds);
            model.addAttribute("myLikedReviewIds", myLikedReviewIds);
        }

        List<CommentResponse> commentList = commentService.getCommentList(bookId);
        model.addAttribute("commentList", commentList);

        return "book/detail";
    }
}
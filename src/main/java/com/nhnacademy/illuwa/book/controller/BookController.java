package com.nhnacademy.illuwa.book.controller;

import com.nhnacademy.illuwa.auth.utils.JwtCookieUtil;
import com.nhnacademy.illuwa.book.dto.BookDetailResponse;
import com.nhnacademy.illuwa.book.service.BookService;
import com.nhnacademy.illuwa.common.dto.PageResponse;
import com.nhnacademy.illuwa.member.service.MemberService;
import com.nhnacademy.illuwa.review.dto.ReviewResponse;
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
    private final MemberService memberService;

    @GetMapping("/user/book-info/{isbn}")
    public String bookInfo(@PathVariable String isbn, Model model) {
        return "book/book_info";
    }


    //accessToken 검증하는 공통 서비스 이용하기
    @GetMapping("/user/books/{isbn}")
    public String bookDetail(@PathVariable("isbn") String isbn, Model model,
                             HttpServletRequest request) {
        boolean isLoginUser = JwtCookieUtil.checkAccessToken(request);

        BookDetailResponse bookDetail = bookService.findBookByIsbn(isbn);

        Long bookId = bookDetail.getId();
//        PageResponse<ReviewResponse> reviewPage = reviewService.getReviewPages(bookId, 0, 5);
//        List<Long> memberIds = reviewPage.content().stream().map(ReviewResponse::getMemberId).toList();
//        Map<Long,String> nameMap = memberService.getNamesFromIdList(memberIds);

        model.addAttribute("book", bookDetail);
//        model.addAttribute("reviewContent", reviewPage.content());
//        model.addAttribute("reviewPage", reviewPage);
//        model.addAttribute("nameMap", nameMap);

        return "book/detail";
    }
}
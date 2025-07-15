package com.nhnacademy.illuwa.book.controller;

import com.nhnacademy.illuwa.book.client.ProductServiceClient;
import com.nhnacademy.illuwa.book.dto.BookDetailResponse;
import com.nhnacademy.illuwa.book.service.BookService;
import com.nhnacademy.illuwa.common.dto.PageResponse;
import com.nhnacademy.illuwa.member.dto.MemberResponse;
import com.nhnacademy.illuwa.member.service.MemberService;
import com.nhnacademy.illuwa.review.dto.ReviewResponse;
import com.nhnacademy.illuwa.review.service.ReviewService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
@RequiredArgsConstructor
public class BookController {
    MemberService memberService;
    BookService bookService;
    ReviewService reviewService;

    @GetMapping("/user/book_info/{isbn}")
    public String bookInfo(@PathVariable String isbn, Model model) {
        return "book/book_info";
    }


    @GetMapping("/user/books/{isbn}")
    public String bookDetail(@PathVariable("isbn") String isbn, Model model) {
        try {
            MemberResponse member = memberService.getMember();
            model.addAttribute("currentUser", member.getMemberId()); // 로그인 됨
        } catch (FeignException.Unauthorized e) {
            model.addAttribute("currentUser", null); // 로그인 안 됨
        }
        BookDetailResponse bookDetail = bookService.findBookByIsbn(isbn);

        Long bookId = bookDetail.getId();
        PageResponse<ReviewResponse> reviewPage = reviewService.getReviewPages(bookId, 0, 5);

        model.addAttribute("book", bookDetail);
        model.addAttribute("reviewContent", reviewPage.content());
        model.addAttribute("reviewTotalPages", reviewPage.totalPages());
        model.addAttribute("reviewPage", reviewPage);

        return "book/detail";
    }

}
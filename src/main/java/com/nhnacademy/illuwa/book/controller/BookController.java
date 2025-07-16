package com.nhnacademy.illuwa.book.controller;

import com.nhnacademy.illuwa.book.dto.BookDetailResponse;
import com.nhnacademy.illuwa.book.service.BookService;
import com.nhnacademy.illuwa.common.dto.PageResponse;
import com.nhnacademy.illuwa.review.dto.ReviewResponse;
import com.nhnacademy.illuwa.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    private final ReviewService reviewService;

    @GetMapping("/user/book-info/{isbn}")
    public String bookInfo(@PathVariable String isbn, Model model) {
        return "book/book_info";
    }


    @GetMapping("/user/books/{isbn}")
    public String bookDetail(@PathVariable("isbn") String isbn, Model model) {

        BookDetailResponse bookDetail = bookService.findBookByIsbn(isbn);

        Long bookId = bookDetail.getId();
        PageResponse<ReviewResponse> reviewPage = reviewService.getReviewPages(bookId, 0, 5);

        model.addAttribute("book", bookDetail);
        model.addAttribute("reviewContent", reviewPage.content());
        model.addAttribute("reviewPage", reviewPage);

        return "book/detail";
    }
}
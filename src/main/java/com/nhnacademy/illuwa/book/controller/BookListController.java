package com.nhnacademy.illuwa.book.controller;

import com.nhnacademy.illuwa.book.dto.BookDetailResponse;
import com.nhnacademy.illuwa.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookListController {
    private final BookService bookService;

    @GetMapping
    public String listBooks(Model model,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "15") int size,
                            @RequestParam(defaultValue = "id,asc") String sort) {

        Page<BookDetailResponse> bookPage = bookService.getPagedBooks(page, size, sort);

        model.addAttribute("bookPage", bookPage);
        model.addAttribute("sort", sort);

        return "book/book_list";
    }
}

package com.nhnacademy.illuwa.book.controller;

import com.nhnacademy.illuwa.book.dto.BookDetailResponse;
import com.nhnacademy.illuwa.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @PostMapping("/search")
    public String searchBook(@RequestParam("keyword") String keyword, Model model) {
        BookDetailResponse book = bookService.bookDetail(keyword);
        List<BookDetailResponse> books = new ArrayList<>();
        books.add(book);

        model.addAttribute("bookPage", new PageImpl<>(books, PageRequest.of(0, books.size()), books.size()));
        model.addAttribute("books", books);
        return "book/book_list";
    }


}

package com.nhnacademy.illuwa.book.controller;

import com.nhnacademy.illuwa.book.dto.BookDetailResponse;
import com.nhnacademy.illuwa.book.dto.SearchBookResponse;
import com.nhnacademy.illuwa.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class BookListController {
    private final BookService bookService;

//    @GetMapping("/books/list")
//    public String listBooks(Model model) {
//        List<BookDetailResponse> books = bookService.getAllBooks();
//        model.addAttribute("books", books);
//        return "book/book_list";
//    }

    @GetMapping("/books/list")
    public String listBooks(Model model,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "15") int size,
                            @RequestParam(defaultValue = "id,asc") String sort) {

        Page<BookDetailResponse> bookPage = bookService.getPagedBooks(page, size, sort);

        model.addAttribute("bookPage", bookPage);
        model.addAttribute("sort", sort);

        return "book/book_list";
    }

    @PostMapping("/book_search")
    public String searchBook(@RequestParam("keyword") String keyword, Model model) {
        BookDetailResponse book = bookService.bookDetail(keyword);
        List<BookDetailResponse> books = new ArrayList<>();
        books.add(book);

        model.addAttribute("books", books);
        return "book/book_list";
    }


}

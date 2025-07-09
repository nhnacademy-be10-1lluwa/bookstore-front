package com.nhnacademy.illuwa.book.controller;

import com.nhnacademy.illuwa.book.dto.BookDetailResponse;
import com.nhnacademy.illuwa.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
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

//    @GetMapping("/book_list")
//    public String bookList(Model model) {
//        List<SearchBookResponse> books = bookService.bookList();
//        model.addAttribute("books", books);
//        return "book/book_list";
//    }
//
    @PostMapping("/book_search")
    public String searchBook(@RequestParam("keyword") String keyword, Model model) {
        BookDetailResponse book = bookService.bookDetail(keyword);
        List<BookDetailResponse> books = new ArrayList<>();
        books.add(book);

        model.addAttribute("books", books);
        return "book/book_list";
    }

    // 뷰(HTML) 반환
    @GetMapping(value="/books", produces= MediaType.TEXT_HTML_VALUE)
    public String viewBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "book/book_list";
    }

    // JSON API 반환
    @GetMapping(value="/books", produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<BookDetailResponse> booksJson() {
        return bookService.getAllBooks();
    }

}

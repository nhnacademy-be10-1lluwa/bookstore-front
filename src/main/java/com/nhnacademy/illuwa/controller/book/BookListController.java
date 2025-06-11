package com.nhnacademy.illuwa.controller.book;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BookListController {
    @GetMapping("/book_list")
    public String bookList() {
        return "book/book_list";
    }
}

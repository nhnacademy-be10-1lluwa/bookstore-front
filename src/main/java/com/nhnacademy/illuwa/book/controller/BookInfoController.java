package com.nhnacademy.illuwa.book.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class BookInfoController {
    @GetMapping("/book_info/{isbn}")
    public String bookInfo(@PathVariable String isbn, Model model) {
        return "book/book_info";
    }
}

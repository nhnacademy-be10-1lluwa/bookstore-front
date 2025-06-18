package com.nhnacademy.illuwa.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AdminBookController {
    @GetMapping("/admin/book/books")
    public String book() {
        return "admin/book/books";
    }

    @GetMapping("/admin/book/book_register")
    public String book_register() {
        return "admin/book/book_register";
    }

    @GetMapping("/admin/book/book_detail/{isbn}")
    public String book_detail(@PathVariable String isbn) {
        return "admin/book/book_detail";
    }
}

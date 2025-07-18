package com.nhnacademy.illuwa.booklike.controller;

import com.nhnacademy.illuwa.booklike.service.BookLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class BookLikeController {
    private final BookLikeService bookLikeService;

    @GetMapping("/book-likes")
    public String getBookLikeList(Model model){
        model.addAttribute("activeMenu", "book-like");
        return "mypage/section/book_likes";
    }
}

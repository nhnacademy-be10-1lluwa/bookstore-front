package com.nhnacademy.illuwa.booklike.controller;

import com.nhnacademy.illuwa.booklike.service.BookLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class BookLikeController {
    private final BookLikeService bookLikeService;

    @GetMapping("/book-likes")
    public String getBookLikeList(Model model){
        model.addAttribute("activeMenu", "book-likes");
        return "mypage/section/book_likes";
    }

    @PostMapping("/book-likes/toggle")
    public String toggleBookLike(@RequestParam Long bookId,
                                 @RequestParam String bookIsbn){
        bookLikeService.toggleBookLikes(bookId);
        return "redirect:/books/" + bookIsbn;
    }
}

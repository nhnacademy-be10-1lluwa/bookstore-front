package com.nhnacademy.illuwa.booklike.controller;

import com.nhnacademy.illuwa.book.dto.SimpleBookResponse;
import com.nhnacademy.illuwa.booklike.service.BookLikeService;
import com.nhnacademy.illuwa.common.dto.PageResponse;
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
    public String getBookLikeList(Model model,
                                  @RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "5") int size){
        PageResponse<SimpleBookResponse> bookList = bookLikeService.getLikedBooksByMember(page, size);
        model.addAttribute("likedBooks", bookList);
        model.addAttribute("activeMenu", "book-likes");
        return "mypage/section/book_likes";
    }

    @PostMapping("/book-likes/toggle")
    public String toggleBookLike(@RequestParam Long bookId){
        bookLikeService.toggleBookLikes(bookId);
        return "redirect:/books/" + bookId;
    }
}

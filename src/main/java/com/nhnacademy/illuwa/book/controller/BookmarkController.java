package com.nhnacademy.illuwa.book.controller;

import com.nhnacademy.illuwa.book.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/bookmarks")
public class BookmarkController {
    private final BookmarkService bookmarkService;

    public Boolean toggleBookmark(@RequestParam long bookId) {
        return bookmarkService.toggleBookmark(bookId);
    }
}

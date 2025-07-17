package com.nhnacademy.illuwa.bookmark.controller;

import com.nhnacademy.illuwa.bookmark.service.BookMarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class BookMarkController {
    private final BookMarkService bookMarkService;

    @GetMapping("/bookmark")
    public String getBookMarkList(Model model){
        model.addAttribute("activeMenu", "bookmark");
        return "mypage/section/bookmark";
    }
}

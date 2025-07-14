package com.nhnacademy.illuwa.book.controller;

import com.nhnacademy.illuwa.member.dto.MemberResponse;
import com.nhnacademy.illuwa.member.service.MemberService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class BookInfoController {
    private final MemberService memberService;

    @GetMapping("/book_info/{isbn}")
    public String bookInfo(@PathVariable String isbn, Model model) {
        try {
            MemberResponse member = memberService.getMember();
            model.addAttribute("currentUser", member.getMemberId()); // 로그인 됨
        } catch (FeignException.Unauthorized e) {
            model.addAttribute("currentUser", null); // 로그인 안 됨
        }

        return "book/book_info";
    }
}

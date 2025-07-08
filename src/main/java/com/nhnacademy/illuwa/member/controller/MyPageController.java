package com.nhnacademy.illuwa.member.controller;

import com.nhnacademy.illuwa.member.dto.MemberResponse;
import com.nhnacademy.illuwa.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MyPageController {
    private final MemberService memberService;

    @GetMapping("/mypage")
    public String myPage( Model model) {
        MemberResponse member = memberService.getMember();
        model.addAttribute("member", member);
        return "mypage/mypage";
    }
}

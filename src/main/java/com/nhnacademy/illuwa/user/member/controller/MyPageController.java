package com.nhnacademy.illuwa.user.member.controller;

import com.nhnacademy.illuwa.user.member.dto.MemberResponse;
import com.nhnacademy.illuwa.user.member.client.MemberServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MyPageController {
    private final MemberServiceClient memberServiceClient;

    @GetMapping("/mypage")
    public String myPage( Model model) {
        MemberResponse member = memberServiceClient.getMember();
        model.addAttribute("member", member);
        return "user/mypage";
    }

    @GetMapping("/userinfo")
    public String userInfo(Model model) {
        MemberResponse member = memberServiceClient.getMember();
        model.addAttribute("member", member);
        return "user/userinfo";
    }
}

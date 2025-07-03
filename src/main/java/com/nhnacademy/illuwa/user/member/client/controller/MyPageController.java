package com.nhnacademy.illuwa.user.member.client.controller;

import com.nhnacademy.illuwa.auth.dto.MemberResponse;
import com.nhnacademy.illuwa.user.member.client.MemberServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.lang.reflect.Member;

@Controller
@RequiredArgsConstructor
public class MyPageController {
    private final MemberServiceClient memberServiceClient;

/*    @GetMapping("/mypage")
    public String myPage(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        Long memberId = userDetails.getId();
        MemberResponse member = memberServiceClient.getMember(memberId);
        model.addAttribute("member", member);
        return "user/mypage";
    }*/


/*    @GetMapping("/userinfo")
    public String userInfo(Model model) {
        MemberResponse member = memberServiceClient.getMember();
        model.addAttribute("member", member);
        return "user/userinfo";
    }*/
}

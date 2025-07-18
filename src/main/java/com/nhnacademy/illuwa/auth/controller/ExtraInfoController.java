package com.nhnacademy.illuwa.auth.controller;

import com.nhnacademy.illuwa.auth.dto.payco.PaycoMemberUpdateRequest;
import com.nhnacademy.illuwa.member.client.MemberServiceClient;
import com.nhnacademy.illuwa.member.dto.MemberResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class ExtraInfoController {

    private final MemberServiceClient  memberServiceClient;

    @GetMapping("/extra-info")
    public String extraInfo(Model model) {
        MemberResponse member = memberServiceClient.getMember();
        model.addAttribute("member", member);
        return "auth/extra-info";
    }

    @PostMapping("/extra-info")
    public String extraInfoSubmit(@Valid @ModelAttribute PaycoMemberUpdateRequest memberRegisterRequest) {
        memberServiceClient.updatePaycoMember(memberRegisterRequest);
        return "redirect:/";
    }
}

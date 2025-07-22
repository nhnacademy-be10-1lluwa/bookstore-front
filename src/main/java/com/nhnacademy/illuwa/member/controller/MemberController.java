package com.nhnacademy.illuwa.member.controller;

import com.nhnacademy.illuwa.common.exception.BackendApiException;
import com.nhnacademy.illuwa.member.dto.MemberResponse;
import com.nhnacademy.illuwa.member.dto.MemberUpdateRequest;
import com.nhnacademy.illuwa.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    // 내 정보 보기
    @GetMapping("/my-info")
    public String memberInfo(Model model,
                             @ModelAttribute("form") MemberUpdateRequest form,
                             @ModelAttribute("mode") String mode) {

        MemberResponse member = memberService.getMember();

        // model에 form이 없거나 view일 때 기본 정보로 세팅
        if (!model.containsAttribute("form") || !StringUtils.hasText(mode) || !mode.equals("edit")) {
            model.addAttribute("form", MemberUpdateRequest.builder()
                    .name(member.getName())
                    .contact(member.getContact())
                    .build());
        }

        model.addAttribute("member", member);
        model.addAttribute("mode", StringUtils.hasText(mode) ? mode : "view");
        model.addAttribute("activeMenu", "my-info");

        return "mypage/section/myinfo";
    }

    // 내 정보 수정
    @PostMapping("/my-info/update")
    public String updateMember(@Valid MemberUpdateRequest request,
                               RedirectAttributes redirectAttributes){
        //정상 수정
        try{
            memberService.updateMember(request);
            redirectAttributes.addFlashAttribute("message", "회원정보가 정상적으로 수정되었습니다!");
        } catch (BackendApiException e) {
            redirectAttributes.addFlashAttribute("mode", "edit");
            redirectAttributes.addFlashAttribute("form", request);
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/my-info";
    }

    // 내 계정 삭제
    @PostMapping("/my-info/delete")
    @ResponseBody
    public ResponseEntity<Void> deleteMember() {
        memberService.deleteMember();
        return ResponseEntity.ok().build();
    }
}
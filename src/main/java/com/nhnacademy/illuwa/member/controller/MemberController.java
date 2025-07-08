package com.nhnacademy.illuwa.member.controller;

import com.nhnacademy.illuwa.member.dto.MemberResponse;
import com.nhnacademy.illuwa.member.dto.MemberUpdateRequest;
import com.nhnacademy.illuwa.member.dto.PasswordCheckRequest;
import com.nhnacademy.illuwa.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

        return "mypage/section/myinfo";
    }



    @PostMapping("/my-info/update")
    public String updateMember(@Valid MemberUpdateRequest request,
                               RedirectAttributes redirectAttributes){

        if (!StringUtils.hasText(request.getName()) || !StringUtils.hasText(request.getCurrentPassword())) {
            redirectAttributes.addFlashAttribute("error", "필수 입력값을 모두 채워주세요!");
            return "redirect:/my-info";
        }

        // 비밀번호 조건 수동 검사
        if (StringUtils.hasText(request.getPassword())) {
            String pattern = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}";
            if (!request.getPassword().matches(pattern)) {
                redirectAttributes.addFlashAttribute("error", "비밀번호는 8~16자 영문+숫자+특수문자 형식이어야 합니다.");
                return "redirect:/my-info";
            }
        } else if ("".equals(request.getPassword())) {
            request.setPassword(null);
        }

        // 연락처 조건 수동 검사
        if (StringUtils.hasText(request.getContact())) {
            String contactPattern = "^010-\\d{3,4}-\\d{4}$";
            if (!request.getContact().matches(contactPattern)) {
                redirectAttributes.addFlashAttribute("error", "연락처 형식이 올바르지 않습니다.");
                return "redirect:/my-info";
            }
        }

        // 현재 비밀번호 일치 확인
        boolean isValid = memberService.checkPassword(new PasswordCheckRequest(request.getCurrentPassword()));
        if(!isValid) {
            redirectAttributes.addFlashAttribute("message", "현재 비밀번호가 일치하지 않습니다.");
            return "redirect:/my-info";
        }
        //정상 수정
        try{
            memberService.updateMember(request);
            redirectAttributes.addFlashAttribute("message", "회원정보가 정상적으로 수정되었습니다!");
        } catch (Exception e){
            log.error("회원정보 수정 중 오류 발생: {}", e.getMessage());

            redirectAttributes.addFlashAttribute("mode", "edit");
            redirectAttributes.addFlashAttribute("form", request);
            redirectAttributes.addFlashAttribute("error", "회원정보 수정 중 오류가 발생했어요. 다시 시도해주세요! 😢");
        }
        return "redirect:/my-info";
    }

    @PostMapping("/my-info/delete")
    public String deleteMember(){
        memberService.deleteMember();
        return "redirect:/logout";
    }
}
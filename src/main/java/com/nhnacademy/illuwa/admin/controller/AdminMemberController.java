package com.nhnacademy.illuwa.admin.controller;

import com.nhnacademy.illuwa.common.dto.PageResponse;
import com.nhnacademy.illuwa.member.enums.GradeName;
import com.nhnacademy.illuwa.member.dto.MemberResponse;
import com.nhnacademy.illuwa.member.service.AdminMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminMemberController {

    private final AdminMemberService adminMemberService;

    // 회원 관리 페이지
    @GetMapping("/members/management")
    public String adminMemberManagement() {
        return "admin/member/member_management";
    }

    // 회원 리스트
    @GetMapping("/members")
    public String memberList(
            @RequestParam(value = "grade", required = false) GradeName gradeName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        PageResponse<MemberResponse> memberPage =
                adminMemberService.getPagedMemberList(gradeName, page, size);

        model.addAttribute("memberList", memberPage.content());
        model.addAttribute("memberCount",memberPage.totalElements());
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", memberPage.size());
        model.addAttribute("totalPages", memberPage.totalPages());
        model.addAttribute("lastPageIndex", Math.max(0, memberPage.totalPages() - 1));
        model.addAttribute("selectedGrade", gradeName != null ? gradeName.name() : "");
        return "admin/member/member_list";
    }

    // 등급별 포인트 적립
    @PostMapping("/members/grade/bonus")
    public String giveBonus(@RequestParam("grade") GradeName gradeName,
                            @RequestParam("point") BigDecimal point,
                            RedirectAttributes redirectAttributes) {
        adminMemberService.givePointToGrade(gradeName, point);
//        redirectAttributes.addAttribute("message", gradeName.getName() + "등급의 회원들에게 보너스 포인트 지급이 완료됐습니다!");
        return "redirect:/admin/members?grade=" + gradeName;
    }

    // 회원 주문 내역
    @GetMapping("/members/orders")
    public String memberOrderList() {
        return "admin/member/member_orderlist";
    }

    // 비회원 주문 내역
    @GetMapping("/guests/orders")
    public String guestOrderList() {
        return "admin/member/guest_orderlist";
    }
}

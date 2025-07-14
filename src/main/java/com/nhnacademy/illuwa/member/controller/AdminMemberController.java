package com.nhnacademy.illuwa.member.controller;

import com.nhnacademy.illuwa.common.dto.PageResponse;
import com.nhnacademy.illuwa.grade.enums.GradeName;
import com.nhnacademy.illuwa.member.dto.MemberResponse;
import com.nhnacademy.illuwa.member.service.AdminMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;

@Controller
@RequiredArgsConstructor
public class AdminMemberController {

    private final AdminMemberService adminMemberService;

    @GetMapping("/admin/member-management")
    public String adminMemberManagement() {
        return "admin/member/member_management";
    }

    @GetMapping("/admin/member-list")
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

    //등급별 포인트 적립
    @PostMapping("/admin/point-bonus")
    public String giveBonus(@RequestParam("grade") GradeName gradeName,
                            @RequestParam("point") BigDecimal point,
                            RedirectAttributes redirectAttributes) {
        adminMemberService.givePointToGrade(gradeName, point);
//        redirectAttributes.addAttribute("message", gradeName.getName() + "등급의 회원들에게 보너스 포인트 지급이 완료됐습니다!");
        return "redirect:/admin/member-list?grade=" + gradeName;
    }


    @GetMapping("/admin/member-orderlist")
    public String memberOrderList() {
        return "admin/member/member_orderlist";
    }

    @GetMapping("/admin/guest-orderlist")
    public String guestOrderList() {
        return "admin/member/guest_orderlist";
    }


}

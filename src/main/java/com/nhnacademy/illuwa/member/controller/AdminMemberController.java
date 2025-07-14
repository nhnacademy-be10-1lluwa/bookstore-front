package com.nhnacademy.illuwa.member.controller;

import com.nhnacademy.illuwa.common.dto.PageResponse;
import com.nhnacademy.illuwa.member.dto.MemberResponse;
import com.nhnacademy.illuwa.member.service.AdminMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {
        PageResponse<MemberResponse> memberPage =
                adminMemberService.getPagedMemberList(page, size);

        model.addAttribute("memberList", memberPage.content());
        model.addAttribute("memberCount",memberPage.totalElements());
        model.addAttribute("currentPage", memberPage.page());
        model.addAttribute("pageSize", memberPage.size());
        model.addAttribute("totalPages", memberPage.totalPages());
        model.addAttribute("lastPageIndex", Math.max(0, memberPage.totalPages() - 1));
        return "admin/member/member_list";
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

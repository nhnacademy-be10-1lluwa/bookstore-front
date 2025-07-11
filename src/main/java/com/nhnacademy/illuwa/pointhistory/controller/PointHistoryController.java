package com.nhnacademy.illuwa.pointhistory.controller;

import com.nhnacademy.illuwa.memberaddress.dto.MemberAddressResponse;
import com.nhnacademy.illuwa.memberaddress.dto.PageResponse;
import com.nhnacademy.illuwa.pointhistory.dto.PointHistoryResponse;
import com.nhnacademy.illuwa.pointhistory.service.PointHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class PointHistoryController {
    private final PointHistoryService pointHistoryService;

    @GetMapping("/point-history")
    public String getPointHistoryList(
            @RequestParam(defaultValue = "ALL") String type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size,
            Model model){

        PageResponse<PointHistoryResponse> pointHistoryPage =
                pointHistoryService.getPagedMemberPointHistoryList(type, page, size);

        model.addAttribute("currentPoint", pointHistoryService.getMemberPoint());
        model.addAttribute("pointHistoryList", pointHistoryPage.getContent());
        model.addAttribute("currentPage", pointHistoryPage.getNumber());
        model.addAttribute("pageSize", pointHistoryPage.getSize());
        model.addAttribute("totalPages", pointHistoryPage.getTotalPages());
        model.addAttribute("lastPageIndex", Math.max(0, pointHistoryPage.getTotalPages() - 1));
        model.addAttribute("currentType", type);
        return "mypage/section/point_history";
    }

}

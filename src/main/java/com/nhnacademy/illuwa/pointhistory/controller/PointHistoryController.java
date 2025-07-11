package com.nhnacademy.illuwa.pointhistory.controller;

import com.nhnacademy.illuwa.pointhistory.service.PointHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class PointHistoryController {
    private final PointHistoryService pointHistoryService;

    @GetMapping("/point-history")
    public String getPointHistoryList(Model model){
        model.addAttribute("currentPoint", pointHistoryService.getMemberPoint());
        model.addAttribute("pointHistoryList", pointHistoryService.getMemberPointHistoryList());
        return "/mypage/section/point_history";
    }

}

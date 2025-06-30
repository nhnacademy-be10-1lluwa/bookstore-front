package com.nhnacademy.illuwa.admin.controller;

import com.nhnacademy.illuwa.order.dto.PackagingRequestDto;
import com.nhnacademy.illuwa.order.dto.PackagingResponseDto;
import com.nhnacademy.illuwa.order.service.PackagingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/packaging")
public class AdminPackagingController {

    private final PackagingService packagingService;

    @GetMapping("/packaging")
    public String packaging(Model model) {

        List<PackagingResponseDto> packaging = packagingService.getPackagingListFromApi();

        model.addAttribute("packaging", packaging);
        model.addAttribute("packagingRequestDto", new PackagingRequestDto());
        return "order/packaging_list";
    }

    @PostMapping("/packaging")
    public String registerPackagingSubmit(PackagingRequestDto packagingRequestDto) {
        PackagingResponseDto packagingResponseDto = packagingService.registerPackaging(packagingRequestDto);
        return "redirect:/admin/packaging/packaging";
    }
}

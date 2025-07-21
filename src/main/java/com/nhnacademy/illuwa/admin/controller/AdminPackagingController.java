package com.nhnacademy.illuwa.admin.controller;

import com.nhnacademy.illuwa.order.dto.command.create.PackagingRequest;
import com.nhnacademy.illuwa.order.dto.query.info.PackagingResponse;
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

    @GetMapping
    public String packaging(Model model) {

        List<PackagingResponse> packaging = packagingService.getAllPackagingOptions();

        model.addAttribute("packaging", packaging);
        model.addAttribute("packagingRequestDto", new PackagingRequest());
        return "order/packaging_list";
    }

    @PostMapping
    public String registerPackagingSubmit(PackagingRequest packagingRequest) {
        packagingService.createPackaging(packagingRequest);
        return "redirect:/admin/packaging";
    }
}

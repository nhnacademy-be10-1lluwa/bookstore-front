package com.nhnacademy.illuwa.pointpolicy.controller;

import com.nhnacademy.illuwa.pointpolicy.dto.PointPolicyCreateRequest;
import com.nhnacademy.illuwa.pointpolicy.dto.PointPolicyUpdateRequest;
import com.nhnacademy.illuwa.pointpolicy.service.AdminPointPolicyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/policy/point")
public class AdminPointPolicyController {

    private final AdminPointPolicyService adminPointPolicyService;

    @GetMapping
    public String getPointPolicyList(Model model) {
        model.addAttribute("pointPolicyList", adminPointPolicyService.getPointPolicyList());
        return "admin/policy/point_policy_view_list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("request", adminPointPolicyService.prepareCreateForm());
        model.addAttribute("mode", "new");
        return "admin/policy/point_policy_form";
    }

    @PostMapping("/create")
    public String createPolicy(@Valid PointPolicyCreateRequest request) {
        adminPointPolicyService.createPolicy(request);
        return "redirect:/admin/policy/point";
    }

    @GetMapping("/{policyKey}/update")
    public String showUpdateForm(@PathVariable String policyKey, Model model) {
        PointPolicyUpdateRequest request = adminPointPolicyService.prepareUpdateForm(policyKey);
        String displayView = adminPointPolicyService.getDisplayView(policyKey);

        model.addAttribute("request", request);
        model.addAttribute("policyKey", policyKey);
        model.addAttribute("displayView", displayView);
        model.addAttribute("mode", "edit");
        return "admin/policy/point_policy_form";
    }

    @PostMapping("/{policyKey}/update")
    public String updatePolicy(@PathVariable String policyKey,
                               @Valid PointPolicyUpdateRequest request) {
        adminPointPolicyService.updatePolicy(policyKey, request);
        return "redirect:/admin/policy/point";
    }

    @GetMapping("/{policyKey}/view")
    public String showDetail(@PathVariable String policyKey, Model model) {
        model.addAttribute("request", adminPointPolicyService.prepareViewForm(policyKey));
        model.addAttribute("policyKey", policyKey);
        model.addAttribute("mode", "view");
        return "admin/policy/point_policy_form";
    }

    @PostMapping("/{policyKey}/delete")
    public String deletePolicy(@PathVariable String policyKey) {
        adminPointPolicyService.deletePolicy(policyKey);
        return "redirect:/admin/policy/point";
    }
}

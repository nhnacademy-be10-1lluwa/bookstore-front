package com.nhnacademy.illuwa.admin.controller;

import com.nhnacademy.illuwa.coupon.dto.couponPolicy.CouponPolicyFrom;
import com.nhnacademy.illuwa.coupon.dto.couponPolicy.CouponPolicyResponse;
import com.nhnacademy.illuwa.coupon.dto.couponPolicy.CouponPolicyUpdateRequest;
import com.nhnacademy.illuwa.coupon.service.CouponPolicyService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/admin/policies")
public class AdminPolicyController {

    private final CouponPolicyService couponPolicyService;

    // 정책 대시보드
    @GetMapping
    public String policy() {
        return "admin/policy/policy";
    }

    // 쿠폰 정책 목록 페이지
    @GetMapping("/coupons")
    public String couponPolicyList(Model model) {
        List<CouponPolicyResponse> couponPolicies = couponPolicyService.getAllPolices();
        model.addAttribute("couponPolicies", couponPolicies);
        return "admin/policy/coupon_policy_view_list";
    }

    // 쿠폰 정책 등록 폼
    @GetMapping("/coupons/form")
    public String couponPolicyCreateFrom(Model model) {
        model.addAttribute("policyFrom", new CouponPolicyFrom());
        return "admin/policy/coupon_policy_create";
    }

    // 쿠폰 정책 폼 데이터 전송
    @PostMapping("/coupons")
    public String registerCouponPolicy(@ModelAttribute @Valid CouponPolicyFrom policyForm, Model model, RedirectAttributes redirectAttributes) {
        couponPolicyService.createCouponPolicy(policyForm);
        redirectAttributes.addFlashAttribute("msg", "정책 등록 성공!");
        return "redirect:/admin/policies/coupons";

    }

    // 쿠폰 정책 수정 페이지 반환
    @GetMapping("/coupons/{code}/form")
    public String showUpdateForm(@PathVariable String code, Model model) {
        CouponPolicyResponse policy = couponPolicyService.getPolicyByCode(code);
        model.addAttribute("policy", policy);
        return "admin/policy/coupon_policy_update";
    }

    // 쿠폰 정책 수정 데이터 전송
    @PostMapping("/coupons/{code}/update")
    public String updatePolicy(
            @PathVariable String code,
            @ModelAttribute CouponPolicyUpdateRequest updateRequest) {
        couponPolicyService.updateCouponPolicy(code, updateRequest);
        // 성공 시 목록으로 리다이렉트
        return "redirect:/admin/policies/coupons";
    }

    // 쿠폰 정책 삭제 (= 비활성화)
    @PostMapping("/coupons/{code}/delete")
    public String deletePolicy(@PathVariable String code) {
        couponPolicyService.deleteCouponPolicy(code); // status INACTIVE로 변경
        return "redirect:/admin/policies/coupons";
    }
}

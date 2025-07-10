package com.nhnacademy.illuwa.admin.controller;

import com.nhnacademy.illuwa.coupon.dto.coupon.CouponForm;
import com.nhnacademy.illuwa.coupon.dto.coupon.CouponResponse;
import com.nhnacademy.illuwa.coupon.dto.coupon.CouponUpdateRequest;
import com.nhnacademy.illuwa.coupon.service.CouponService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class AdminCouponController {

    private final CouponService couponService;

    // 쿠폰 목록 페이지
    @GetMapping("/admin/coupon/coupon")
    public String couponList(Model model) {
        List<CouponResponse> coupons = couponService.getAllCoupons();
        model.addAttribute("coupons", coupons);
        return "admin/coupon/coupon_view_list";
    }


    // 쿠폰 등록 폼
    @GetMapping("/admin/coupon/coupon/create")
    public String couponPolicyCreateFrom(Model model) {
        model.addAttribute("couponForm", new CouponForm());
        return "admin/coupon/coupon_create";
    }

    // 쿠폰 폼 데이터 전송
    @PostMapping("/admin/coupon/coupon/create")
    public String registerCoupon(@ModelAttribute @Valid CouponForm couponForm) {
        couponService.createCoupon(couponForm);
        return "redirect:/admin/coupon/coupon";
    }

    // 쿠폰 수정 폼
    @GetMapping("/admin/coupon/coupon/{id}/update")
    public String showCouponUpdateForm(@PathVariable Long id, Model model) {
        CouponResponse coupon = couponService.getCouponById(id);
        model.addAttribute("coupon", coupon);
        return "admin/coupon/coupon_update";
    }

    // 쿠폰 수정 데이터 전송
    @PostMapping("/admin/coupon/coupon/{id}/update")
    public String updateCoupon(@PathVariable Long id, @ModelAttribute CouponUpdateRequest request) {
        couponService.updateCoupon(id, request);
        return "redirect:/admin/coupon/coupon";
    }


    // 쿠폰 삭제
    @PostMapping("/admin/coupon/coupon/{id}/delete")
    public String deleteCoupon(@PathVariable Long id) {
        couponService.deleteCoupon(id);
        return "redirect:/admin/coupon/coupon";
    }
}

package com.nhnacademy.illuwa.coupon.controller;

import com.nhnacademy.illuwa.coupon.dto.MemberCouponIssueRequest;
import com.nhnacademy.illuwa.coupon.dto.coupon.CouponResponse;
import com.nhnacademy.illuwa.coupon.service.CouponService;
import com.nhnacademy.illuwa.coupon.service.MemberCouponService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/coupons")
public class CouponController {
    private final MemberCouponService memberCouponService;
    private final CouponService couponService;
    @GetMapping
    public String coupons(Model model) {
        List<CouponResponse> coupons = couponService.getAllCoupons();
        model.addAttribute("coupons", coupons);
        return "user/couponpage";
    }

    @PostMapping("/issue")
    public String issueCoupon(MemberCouponIssueRequest request) {
        memberCouponService.issueCoupon(request);
        return "redirect:/coupons";
    }
}

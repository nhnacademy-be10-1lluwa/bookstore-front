package com.nhnacademy.illuwa.coupon.controller;

import com.nhnacademy.illuwa.coupon.dto.MemberCouponResponse;
import com.nhnacademy.illuwa.coupon.service.MemberCouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class MyCouponController {
    private final MemberCouponService memberCouponService;

    @GetMapping("/mycoupon")
    public String myCoupon(Model model) {
        List<MemberCouponResponse> couponResponses = memberCouponService.getAllMemberCoupons();

       /* List<MemberCouponResponse> availableCoupons = couponResponses.stream()
                .filter(coupon -> !coupon.isUsed())
                .toList();

        List<MemberCouponResponse> usedCoupons = couponResponses.stream()
                .filter(MemberCouponResponse::isUsed)
                .toList();

        model.addAttribute("availableCoupons", availableCoupons);
        model.addAttribute("usedCoupons", usedCoupons);*/

        return "user/mycoupon";
    }
}

package com.nhnacademy.illuwa.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminCouponController {
    @GetMapping("/admin/coupon/coupon")
    public String coupon() {
        return "admin/coupon/coupon";
    }
}

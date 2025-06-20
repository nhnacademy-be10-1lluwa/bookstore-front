package com.nhnacademy.illuwa.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminPolicyController {
    @GetMapping("/admin/policy/policy")
    public String policy() {
        return "admin/policy/policy";
    }

    @GetMapping("/admin/policy/coupon")
    public String coupon() {
        return "admin/policy/coupon";
    }

    @GetMapping("/admin/policy/point")
    public String point() {
        return "admin/policy/point";
    }
}

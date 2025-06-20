package com.nhnacademy.illuwa.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminMemberController {
    @GetMapping("/admin/member/member")
    public String member() {
        return "admin/member/member";
    }

    @GetMapping("/admin/member/order")
    public String order() {
        return "admin/member/order";
    }

    @GetMapping("/admin/member/address")
    public String address() {
        return "admin/member/address";
    }
}

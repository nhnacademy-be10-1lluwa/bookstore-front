package com.nhnacademy.illuwa.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminHomeController {
    @GetMapping("/admin_home")
    public String home() {
        return "admin/admin_home";
    }
}

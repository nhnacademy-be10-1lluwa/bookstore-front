package com.nhnacademy.illuwa.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyCouponController {
    @GetMapping("/mycoupon")
    public String myCoupon() {
        return "user/mycoupon";
    }
}

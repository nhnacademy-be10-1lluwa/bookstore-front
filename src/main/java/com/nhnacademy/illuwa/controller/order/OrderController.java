package com.nhnacademy.illuwa.controller.order;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderController {
    @GetMapping("/order_detail")
    public String orderOption() {
        return "order/order_detail";
    }
}

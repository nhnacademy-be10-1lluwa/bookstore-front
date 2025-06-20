package com.nhnacademy.illuwa.order.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderViewController {
    @GetMapping("/order_view")
    public String orderView() {
        return "order/order_view";
    }
}

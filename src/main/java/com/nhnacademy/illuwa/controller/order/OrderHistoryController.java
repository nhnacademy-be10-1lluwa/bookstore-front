package com.nhnacademy.illuwa.controller.order;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderHistoryController {
    @GetMapping("/order_history")
    public String orderHistory() {
        return "order/order_history";
    }
}

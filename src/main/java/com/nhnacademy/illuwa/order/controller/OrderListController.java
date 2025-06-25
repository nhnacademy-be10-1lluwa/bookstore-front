package com.nhnacademy.illuwa.order.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderListController {
    @GetMapping("/order_list")
    public String orderList() {
        return "order/order_list";
    }


}

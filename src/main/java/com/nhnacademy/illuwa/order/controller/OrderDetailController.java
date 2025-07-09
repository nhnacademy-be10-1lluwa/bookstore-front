package com.nhnacademy.illuwa.order.controller;

import com.nhnacademy.illuwa.order.dto.OrderResponse;
import com.nhnacademy.illuwa.order.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class OrderDetailController {
    private final OrderService orderService;

    public OrderDetailController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/order_detail/{orderId}")
    public String orderOption(@PathVariable("orderId") Long orderId, Model model) {
        OrderResponse orderResponse = orderService.getOrderDetail(orderId);
        model.addAttribute("order", orderResponse);
        return "order/order_detail";
    }
}

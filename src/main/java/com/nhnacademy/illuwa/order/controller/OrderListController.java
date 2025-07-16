package com.nhnacademy.illuwa.order.controller;

import com.nhnacademy.illuwa.common.dto.PageResponse;
import com.nhnacademy.illuwa.order.dto.OrderListResponse;
import com.nhnacademy.illuwa.order.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OrderListController {
    private final OrderService orderService;

    public OrderListController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/order-list")
    public String orderList(@RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "10") int size,
                            Model model) {
        PageResponse<OrderListResponse> orderPage = orderService.getMemberOrderHistory(page, size);

        model.addAttribute("orderPage", orderPage);
        model.addAttribute("currentPage", orderPage.page());
        model.addAttribute("totalPages", orderPage.totalPages());

        return "order/order_list";
    }
}

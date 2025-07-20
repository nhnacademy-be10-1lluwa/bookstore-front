package com.nhnacademy.illuwa.order.controller;

import com.nhnacademy.illuwa.common.dto.PageResponse;
import com.nhnacademy.illuwa.order.dto.query.list.OrderListResponse;
import com.nhnacademy.illuwa.order.service.MemberOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class OrderListController {

    private final MemberOrderService memberOrderService;

    @GetMapping("/order-list")
    public String orderList(@RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "10") int size,
                            Model model) {
        /*PageResponse<OrderListResponse> orderPage = orderService.getMemberOrderHistory(page, size);*/

        PageResponse<OrderListResponse> orderPage = memberOrderService.getOrderHistory(page, size);

        model.addAttribute("orderPage", orderPage);
        model.addAttribute("currentPage", orderPage.page());
        model.addAttribute("totalPages", orderPage.totalPages());
        model.addAttribute("activeMenu", "order-list");

        return "order/order_list";
    }
}

package com.nhnacademy.illuwa.admin.controller;

import com.nhnacademy.illuwa.common.dto.PageResponse;
import com.nhnacademy.illuwa.order.dto.OrderListResponse;
import com.nhnacademy.illuwa.order.dto.OrderResponse;
import com.nhnacademy.illuwa.order.dto.types.OrderStatus;
import com.nhnacademy.illuwa.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AdminOrderController {

    private final OrderService orderservice;

    // 결제 완료된 주문들 페이지
    @GetMapping("/admin/order/orders")
    public String getPaidOrders(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, Model model) {
        PageResponse<OrderListResponse> orders = orderservice.getOrderStatusPending(page, size);
        model.addAttribute("orderPage", orders);
        model.addAttribute("currentPage", orders.page());
        model.addAttribute("totalPages", orders.totalPages());
        return "admin/order/orders";
    }

    // 멤버 주문 상세 페이지 <- 여기서 배송중으로 변경 처리
    @GetMapping("/admin/order/orders/{orderId}")
    public String getDetailOrder(@PathVariable("orderId") Long orderId, Model model) {
        OrderResponse order = orderservice.getAdminOrderDetail(orderId);
        model.addAttribute("order", order);
        return "admin/order/order_detail";
    }

    // 상태 변경하기
    @PostMapping("/admin/order/{orderId}/status")
    public String updateStatus(@PathVariable Long orderId, @RequestParam OrderStatus orderStatus) {
        orderservice.updateStatus(orderId, orderStatus);
        return "redirect:/admin/order/orders";
    }

    @PostMapping("/admin/order/order-confirmed-update")
    public String orderConfirmedUpdate() {
        orderservice.orderConfirmedUpdate();
        return "redirect:/admin/";
    }

    @PostMapping("/admin/order/member-grade-update")
    public String memberGradeUpdate() {
        orderservice.memberGradeUpdate();
        return "redirect:/admin/member-list";
    }

    @PostMapping("/admin/order/order-cleanup")
    public String orderCleanUp() {
        orderservice.orderCleanUp();
        return "redirect:/admin/";
    }



}

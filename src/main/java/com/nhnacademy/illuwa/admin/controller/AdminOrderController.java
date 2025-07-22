package com.nhnacademy.illuwa.admin.controller;

import com.nhnacademy.illuwa.common.dto.PageResponse;
import com.nhnacademy.illuwa.order.dto.query.list.OrderListResponse;
import com.nhnacademy.illuwa.order.dto.query.detail.OrderResponse;
import com.nhnacademy.illuwa.order.dto.types.OrderStatus;
import com.nhnacademy.illuwa.order.service.AdminOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/orders")
public class AdminOrderController {

    private final AdminOrderService adminOrderService;

    // 결제 완료된 주문들 페이지
    @GetMapping(params = "order-status")
    public String getPaidOrders(@RequestParam("order-status") OrderStatus orderStatus, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, Model model) {
        PageResponse<OrderListResponse> orders = adminOrderService.listOrdersByStatus(orderStatus, page, size);
        model.addAttribute("orderPage", orders);
        model.addAttribute("currentPage", orders.page());
        model.addAttribute("totalPages", orders.totalPages());
        return "admin/order/orders";
    }

    // 멤버 주문 상세 페이지 <- 여기서 배송중으로 변경 처리
    @GetMapping("/{orderId}")
    public String getDetailOrder(@PathVariable("orderId") Long orderId, Model model) {
        OrderResponse order = adminOrderService.getOrderDetail(orderId);
        model.addAttribute("order", order);
        return "admin/order/order_detail";
    }

    // 상태 변경하기
    @PostMapping("/{orderId}/status")
    public String updateStatus(@PathVariable Long orderId, @RequestParam OrderStatus orderStatus) {
        adminOrderService.updateOrderStatus(orderId, orderStatus);
        return "redirect:/admin/orders?order-status=Pending";
    }

    // 배송일로부터 10일 지난 구매건에 대해 구매 확정 적용하기
    @PostMapping("/order-confirmed-update")
    public String orderConfirmedUpdate() {
        adminOrderService.runOrderConfirmedBatch();
        return "redirect:/admin";
    }

    // 멤버 등급 업데이트 하기
    @PostMapping("/member-grade-update")
    public String memberGradeUpdate() {
        adminOrderService.runMemberGradeBatch();
        return "redirect:/admin/members";
    }

    // 주문일로 부터 3일 동안 AwaitingPayment 상태에 머물어 있는 주문건 삭제하기
    @PostMapping("/order-cleanup")
    public String orderCleanUp() {
        adminOrderService.cleanAwaitingOrders();
        return "redirect:/admin";
    }
}

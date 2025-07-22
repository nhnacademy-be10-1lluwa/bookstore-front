package com.nhnacademy.illuwa.order.controller.member.command;

import com.nhnacademy.illuwa.order.dto.types.ReturnReason;
import com.nhnacademy.illuwa.order.service.CommonOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/orders")
public class MemberOrderStatusController {

    private final CommonOrderService commonOrderService;

    // 주문 상태 변경 - 확정
    @PostMapping("/{order-id}/confirm")
    public String requestConfirmOrder(@PathVariable("order-id") Long orderId) {
        commonOrderService.confirmOrder(orderId);
        return "redirect:/orders/" + orderId;
    }

    // 주문 상태 변경 - 취소
    @PostMapping("/{order-id}/cancel")
    public String requestCancelOrder(@PathVariable("order-id") long orderId) {
        commonOrderService.cancelOrder(orderId);
        return "redirect:/orders";
    }

    // 결제 취소
    @PostMapping("/{order-number}/cancel-payment")
    public String requestCancelPayment(@PathVariable("order-number") String orderNumber) {
        commonOrderService.cancelPayment(orderNumber);
        return "redirect:/orders";
    }

    // 주문 환불
    @PostMapping("/{order-id}/refund")
    public String requestRefundOrder(@PathVariable("order-id") Long orderId, ReturnReason returnReason) {
        commonOrderService.refundOrder(orderId, returnReason);
        return "redirect:/orders/" + orderId;
    }
}

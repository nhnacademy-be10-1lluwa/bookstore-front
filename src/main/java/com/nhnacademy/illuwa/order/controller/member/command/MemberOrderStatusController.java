package com.nhnacademy.illuwa.order.controller.member.command;

import com.nhnacademy.illuwa.member.enums.Status;
import com.nhnacademy.illuwa.order.dto.types.ReturnReason;
import com.nhnacademy.illuwa.order.service.CommonOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberOrderStatusController {

    private final CommonOrderService commonOrderService;

    @PostMapping("/orders/{order-id}/confirm-order")
    public String requestConfirmOrder(@PathVariable("order-id") Long orderId) {
        commonOrderService.confirmOrder(orderId);
        return "redirect:/order-detail/" + orderId;
    }

    @PostMapping("/orders/{order-id}/cancel-order")
    public String requestCancelOrder(@PathVariable("order-id") long orderId) {
        commonOrderService.cancelOrder(orderId);
        return "redirect:/order-list";
    }

    @PostMapping("/orders/{order-number}/cancel-payment")
    public String requestCancelPayment(@PathVariable("order-number") String orderNumber) {
        commonOrderService.cancelPayment(orderNumber);
        return "redirect:/order-list";
    }

    @PostMapping("/orders/{order-id}/refund-order")
    public String requestRefundOrder(@PathVariable("order-id") Long orderId, ReturnReason returnReason) {
        commonOrderService.refundOrder(orderId, returnReason);
        return "redirect:/order-detail/" + orderId;
    }
}

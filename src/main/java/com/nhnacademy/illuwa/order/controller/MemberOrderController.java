package com.nhnacademy.illuwa.order.controller;

import com.nhnacademy.illuwa.order.dto.OrderResponse;
import com.nhnacademy.illuwa.order.dto.member.MemberOrderInitDirectResponse;
import com.nhnacademy.illuwa.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class MemberOrderController {

    private final OrderService orderService;

    @GetMapping("/order/order-form/{bookId}")
    public String showOrderForm(@PathVariable("bookId") Long bookId, Model model) {
        MemberOrderInitDirectResponse memberInfo = orderService.getMemberInitDateDirect(bookId);

        OrderResponse orderResponse = new OrderResponse();
        model.addAttribute("orderRequest", orderResponse);
        model.addAttribute("init", memberInfo);
        return "order/order_member_form";
    }

}

package com.nhnacademy.illuwa.order.controller;

import com.nhnacademy.illuwa.order.dto.OrderCreateResponse;
import com.nhnacademy.illuwa.order.dto.OrderItemResponseDto;
import com.nhnacademy.illuwa.order.dto.member.MemberOrderDirectRequest;
import com.nhnacademy.illuwa.order.dto.member.MemberOrderInitDirectResponse;
import com.nhnacademy.illuwa.order.dto.orderRequest.OrderItemDto;
import com.nhnacademy.illuwa.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequiredArgsConstructor
public class MemberOrderController {

    private final OrderService orderService;

    @GetMapping("/order/order-form/{bookId}")
    public String showOrderForm(@PathVariable("bookId") Long bookId,
                                @RequestParam(name = "quantity", defaultValue = "1") int quantity,
                                Model model) {
        MemberOrderInitDirectResponse memberInfo = orderService.getMemberInitDateDirect(bookId);

        MemberOrderDirectRequest req = new MemberOrderDirectRequest();
        OrderItemDto item = new OrderItemDto();
        item.setBookId(bookId);
        item.setQuantity(quantity);
        req.setItem(item);

        model.addAttribute("orderRequest", req);

        model.addAttribute("init", memberInfo);
        // 템플릿에서 초기 수량 표시용 속성
        model.addAttribute("initialQuantity", quantity);
        return "order/order_member_form";
    }

    @PostMapping("/order/order-form/submit-direct")
    public String sendOrder(@ModelAttribute("orderRequest") MemberOrderDirectRequest request, Model model) {

        OrderCreateResponse response = orderService.sendDirectOrderMember(request);
        model.addAttribute("order", response);

        return "order/order_view";
    }

}

package com.nhnacademy.illuwa.order.controller.guest.command;

import com.nhnacademy.illuwa.order.dto.command.create.GuestOrderDirectRequest;
import com.nhnacademy.illuwa.order.dto.query.info.GuestInfo;
import com.nhnacademy.illuwa.order.dto.query.info.OrderCreateResponse;
import com.nhnacademy.illuwa.order.service.GuestOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/orders/guest")
public class GuestOrderCommandController {

    private final GuestOrderService guestOrderService;

    @Value("${toss.client-key}")
    private String tossClientKey;

    // 비회원 주문 생성
    @PostMapping
    public String sendOrder(@ModelAttribute("orderRequest") GuestOrderDirectRequest request, Model model) {
        OrderCreateResponse response = guestOrderService.createDirectOrder(request);

        GuestInfo guestInfo = new GuestInfo(request.getName(), request.getEmail());

        model.addAttribute("order", response);
        model.addAttribute("member", guestInfo);
        model.addAttribute("tossClientKey", tossClientKey);

        return "order/order_view";
    }
}

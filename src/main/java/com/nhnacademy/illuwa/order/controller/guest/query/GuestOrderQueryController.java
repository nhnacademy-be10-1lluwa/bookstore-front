package com.nhnacademy.illuwa.order.controller.guest.query;

import com.nhnacademy.illuwa.auth.dto.GuestLoginRequest;
import com.nhnacademy.illuwa.guest.dto.GuestResponse;
import com.nhnacademy.illuwa.guest.service.GuestClientService;
import com.nhnacademy.illuwa.order.dto.query.detail.OrderResponse;
import com.nhnacademy.illuwa.order.service.GuestOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class GuestOrderQueryController {

    private final GuestOrderService guestOrderService;
    private final GuestClientService guestClientService;

    @GetMapping("guest-check-order")
    public String showGuestForm() {
        return "order/guest_order_query";
    }

    @PostMapping("/order-history")
    public String guestLogin(@ModelAttribute GuestLoginRequest request, Model model) {
        GuestResponse guest = guestClientService.getGuest(request);
        OrderResponse order = guestOrderService.getOrderHistory(guest.getOrderId());
        model.addAttribute("guest", guest);
        model.addAttribute("order", order);
        return "order/order_history";
    }
}

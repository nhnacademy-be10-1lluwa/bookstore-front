package com.nhnacademy.illuwa.order.controller;

import com.nhnacademy.illuwa.order.client.OrderServiceClient;
import com.nhnacademy.illuwa.order.dto.GuestLoginRequest;
import com.nhnacademy.illuwa.order.dto.OrderResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class GuestOrderController {
    private final OrderServiceClient orderServiceClient;

    public GuestOrderController(OrderServiceClient orderServiceClient) {
        this.orderServiceClient = orderServiceClient;
    }

    @GetMapping("/guest-login")
    public String showGuestLogin(Model model) {
        model.addAttribute("loginRequest", new GuestLoginRequest());
        return "order/guest_login";
    }

    @PostMapping("/guest-login")
    public String guestOrderHistory(@ModelAttribute GuestLoginRequest request, RedirectAttributes attr) {
        attr.addAttribute("orderNumber", request.getOrderNumber());
        attr.addAttribute("contact", request.getRecipientContact());
        return "redirect:/order-history";
    }

    @GetMapping("/order-history")
    public String showOrderHistory(@RequestParam String orderNumber,
                                   @RequestParam String contact,
                                   Model model) {
        OrderResponse dto = orderServiceClient.getGuestOrderHistory(orderNumber, contact);
        model.addAttribute("order", dto);
        return "order/order_history";
    }
}

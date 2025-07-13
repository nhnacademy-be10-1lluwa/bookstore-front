package com.nhnacademy.illuwa.order.controller;

import com.nhnacademy.illuwa.order.client.OrderServiceClient;
import com.nhnacademy.illuwa.order.dto.GuestLoginRequest;
import com.nhnacademy.illuwa.order.dto.OrderCreateResponse;
import com.nhnacademy.illuwa.order.dto.OrderResponse;
import com.nhnacademy.illuwa.order.dto.guest.GuestOrderDirectRequest;
import com.nhnacademy.illuwa.order.dto.guest.GuestOrderInitDirectResponse;
import com.nhnacademy.illuwa.order.dto.orderRequest.OrderItemDto;
import com.nhnacademy.illuwa.order.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class GuestOrderController {
    private final OrderServiceClient orderServiceClient;
    private final OrderService orderService;

    public GuestOrderController(OrderServiceClient orderServiceClient, OrderService orderService) {
        this.orderServiceClient = orderServiceClient;
        this.orderService = orderService;
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

    @GetMapping("/guest/order/order-form/{bookId}")
    public String showOrderForm(@PathVariable("bookId") Long bookId,
                                @RequestParam(name = "quantity", defaultValue = "1") int quantity,
                                Model model) {
        GuestOrderInitDirectResponse guestInfo = orderService.getGuestInitDateDirect(bookId);

        GuestOrderDirectRequest req = new GuestOrderDirectRequest();
        OrderItemDto item = new OrderItemDto();
        item.setBookId(bookId);
        item.setQuantity(quantity);
        req.setItem(item);

        model.addAttribute("orderRequest", req);
        model.addAttribute("init", guestInfo);
        model.addAttribute("initialQuantity", quantity);
        return "order/order_guest_form";
    }

    @PostMapping("/guest/order/order-form/submit-direct")
    public String sendOrder(@ModelAttribute("orderRequest")GuestOrderDirectRequest request, Model model) {
        OrderCreateResponse response = orderService.sendDirectOrderGuest(request);
        model.addAttribute("order", response);

        return "order/order_view";
    }
}

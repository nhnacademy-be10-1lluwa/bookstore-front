package com.nhnacademy.illuwa.order.controller;

import com.nhnacademy.illuwa.guest.dto.GuestResponse;
import com.nhnacademy.illuwa.guest.service.GuestClientService;
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

@Controller
public class GuestOrderController {
    private final OrderServiceClient orderServiceClient;
    private final OrderService orderService;
    private final GuestClientService guestClientService;

    public GuestOrderController(OrderServiceClient orderServiceClient, OrderService orderService, GuestClientService guestClientService) {
        this.orderServiceClient = orderServiceClient;
        this.orderService = orderService;
        this.guestClientService = guestClientService;
    }

    @GetMapping("/guest-login")
    public String showGuestLogin() {
        return "order/guest_login";
    }

    @PostMapping("/order-history")
    public String guestLogin(@ModelAttribute GuestLoginRequest request, Model model) {
        GuestResponse guest = guestClientService.getGuest(request);
        OrderResponse order = orderServiceClient.getGuestOrderHistory(guest.getOrderId());
        model.addAttribute("guest", guest);
        model.addAttribute("order", order);
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

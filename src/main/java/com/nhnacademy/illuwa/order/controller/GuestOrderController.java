package com.nhnacademy.illuwa.order.controller;

import com.nhnacademy.illuwa.guest.dto.GuestResponse;
import com.nhnacademy.illuwa.guest.service.GuestClientService;
import com.nhnacademy.illuwa.auth.dto.GuestLoginRequest;
import com.nhnacademy.illuwa.order.dto.query.info.GuestInfo;
import com.nhnacademy.illuwa.order.dto.query.info.OrderCreateResponse;
import com.nhnacademy.illuwa.order.dto.query.detail.OrderResponse;
import com.nhnacademy.illuwa.order.dto.command.create.GuestOrderDirectRequest;
import com.nhnacademy.illuwa.order.dto.query.info.GuestOrderInitDirectResponse;
import com.nhnacademy.illuwa.order.dto.query.detail.OrderItemResponse;
import com.nhnacademy.illuwa.order.service.GuestOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class GuestOrderController {
    private final GuestOrderService guestOrderService;
    private final GuestClientService guestClientService;

    @Value("${toss.client-key}")
    private String tossClientKey;

    @GetMapping("/guest-login")
    public String showGuestLogin() {
        return "order/guest_login";
    }

    @PostMapping("/order-history")
    public String guestLogin(@ModelAttribute GuestLoginRequest request, Model model) {
        GuestResponse guest = guestClientService.getGuest(request);
        OrderResponse order = guestOrderService.getOrderHistory(guest.getOrderId());
        model.addAttribute("guest", guest);
        model.addAttribute("order", order);
        return "order/order_history";
    }

    @GetMapping("/guest/order/order-form/{bookId}")
    public String showOrderForm(@PathVariable("bookId") Long bookId,
                                @RequestParam(name = "quantity", defaultValue = "1") int quantity,
                                Model model) {
        GuestOrderInitDirectResponse guestInfo = guestOrderService.initDirect(bookId);

        GuestOrderDirectRequest req = new GuestOrderDirectRequest();
        OrderItemResponse item = new OrderItemResponse();
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
        OrderCreateResponse response = guestOrderService.createDirectOrder(request);

        GuestInfo guestInfo = new GuestInfo(request.getName(), request.getEmail());

        model.addAttribute("order", response);
        model.addAttribute("member", guestInfo);
        model.addAttribute("tossClientKey", tossClientKey);

        return "order/order_view";
    }


}

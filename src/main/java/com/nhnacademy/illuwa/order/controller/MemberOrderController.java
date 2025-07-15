package com.nhnacademy.illuwa.order.controller;

import com.nhnacademy.illuwa.order.dto.OrderCreateResponse;
import com.nhnacademy.illuwa.order.dto.member.MemberOrderCartRequest;
import com.nhnacademy.illuwa.order.dto.member.MemberOrderDirectRequest;
import com.nhnacademy.illuwa.order.dto.member.MemberOrderInitDirectResponse;
import com.nhnacademy.illuwa.order.dto.member.MemberOrderInitFromCartResponse;
import com.nhnacademy.illuwa.order.dto.orderRequest.OrderItemDto;
import com.nhnacademy.illuwa.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


@Controller
@RequiredArgsConstructor
public class MemberOrderController {

    private final OrderService orderService;

    @GetMapping("/order/order-form/{bookId}")
    public String showOrderForm(@PathVariable("bookId") Long bookId,
                                @RequestParam(name = "quantity", defaultValue = "1") int quantity,
                                Model model) {
        MemberOrderInitDirectResponse memberInfo = orderService.getMemberInitDataDirect(bookId);
        MemberOrderDirectRequest req = new MemberOrderDirectRequest();

        OrderItemDto item = new OrderItemDto();
        item.setBookId(bookId);
        item.setQuantity(quantity);
        req.setItem(item);

        model.addAttribute("orderRequest", req);
        model.addAttribute("init", memberInfo);
        model.addAttribute("initialQuantity", quantity);
        return "order/order_member_form";
    }

    @GetMapping("/order/cart-order-form")
    public String showOrderFromCartForm(Model model) {
        MemberOrderInitFromCartResponse memberInfo = orderService.getMemberInitDataCart();
        MemberOrderCartRequest req = new MemberOrderCartRequest();

        List<OrderItemDto> items = memberInfo.getCartResponse().getBookCarts()
                        .stream()
                                .map(b -> {
                                    OrderItemDto dto = new OrderItemDto();
                                    dto.setBookId(b.getBookId());
                                    dto.setQuantity(b.getAmount());
                                    dto.setPrice(BigDecimal.valueOf(b.getSalePrice()));
                                    return dto;
                                }).toList();

        req.setCartItems(items);

        model.addAttribute("orderRequest", req);
        model.addAttribute("init", memberInfo);

        return "order/order_member_cart_form";
    }

    @PostMapping("/order/order-form/submit-direct")
    public String sendOrder(@ModelAttribute("orderRequest") MemberOrderDirectRequest request, Model model) {

        OrderCreateResponse response = orderService.sendDirectOrderMember(request);
        model.addAttribute("order", response);

        return "order/order_view";
    }

    @PostMapping("/order/order-form/submit")
    public String sendOrderByCart(@ModelAttribute("orderRequest") MemberOrderCartRequest request, Model model) {

        OrderCreateResponse response = orderService.sendCartOrderMember(request);
        model.addAttribute("order", response);

        return "order/order_cart_view";
    }

}

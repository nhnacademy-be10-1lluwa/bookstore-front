package com.nhnacademy.illuwa.order.controller.member.query;

import com.nhnacademy.illuwa.order.dto.command.create.MemberOrderCartRequest;
import com.nhnacademy.illuwa.order.dto.command.create.MemberOrderDirectRequest;
import com.nhnacademy.illuwa.order.dto.query.detail.OrderItemResponse;
import com.nhnacademy.illuwa.order.dto.query.info.*;
import com.nhnacademy.illuwa.order.service.MemberOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MemberOrderInitController {

    private final MemberOrderService memberOrderService;

    @GetMapping("/order/order-form/{bookId}")
    public String showOrderForm(@PathVariable("bookId") Long bookId,
                                @RequestParam(name = "quantity", defaultValue = "1") int quantity,
                                Model model) {
        MemberOrderInitDirectResponse memberInfo = memberOrderService.initDirect(bookId);
        MemberOrderDirectRequest req = new MemberOrderDirectRequest();

        OrderItemResponse item = new OrderItemResponse();
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
        MemberOrderInitFromCartResponse memberInfo = memberOrderService.initFromCart();
        MemberOrderCartRequest req = new MemberOrderCartRequest();

        List<OrderItemResponse> items = memberInfo.getCartResponse().getBookCarts()
                .stream()
                .map(b -> {
                    OrderItemResponse dto = new OrderItemResponse();
                    dto.setBookId(b.getBookId());
                    dto.setQuantity(b.getAmount());
                    dto.setPrice(BigDecimal.valueOf(b.getSalePrice()));
                    return dto;
                }).toList();

        req.setCartItems(items);

        model.addAttribute("orderRequest", req);
        Map<Long, List<MemberCouponResponse>> map = memberInfo.getCouponMap();
        model.addAttribute("couponMap", map);
        model.addAttribute("init", memberInfo);

        return "order/order_member_cart_form";
    }



}

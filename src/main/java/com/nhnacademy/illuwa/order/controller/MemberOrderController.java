package com.nhnacademy.illuwa.order.controller;


import com.nhnacademy.illuwa.cart.client.CartServiceClient;
import com.nhnacademy.illuwa.member.service.MemberService;
import com.nhnacademy.illuwa.order.dto.OrderCreateResponse;
import com.nhnacademy.illuwa.order.dto.member.*;
import com.nhnacademy.illuwa.order.dto.orderRequest.MemberOrderInfo;
import com.nhnacademy.illuwa.order.dto.orderRequest.OrderItemDto;
import com.nhnacademy.illuwa.order.service.OrderService;
import com.nhnacademy.illuwa.order.service.OrderStatusMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
    private final MemberService memberService;
    private final CartServiceClient cartServiceClient;
    private final OrderStatusMemberService orderStatusMemberService;

    @Value("${toss.client-key}")
    private String tossClientKey;

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
        Map<Long, List<MemberCouponDto>> map = memberInfo.getCouponMap();
        model.addAttribute("couponMap", map);
        model.addAttribute("init", memberInfo);

        return "order/order_member_cart_form";
    }

    @PostMapping("/order/order-form/submit-direct")
    public String sendOrder(@ModelAttribute("orderRequest") MemberOrderDirectRequest request, Model model) {

        OrderCreateResponse response = orderService.sendDirectOrderMember(request);
        model.addAttribute("order", response);
        model.addAttribute("member", getMemberInfo());
        model.addAttribute("tossClientKey", tossClientKey);

        return "order/order_view";
    }

    @PostMapping("/order/order-form/submit")
    public String sendOrderByCart(@ModelAttribute("orderRequest") MemberOrderCartRequest request, Model model) {

        OrderCreateResponse response = orderService.sendCartOrderMember(request);
        cartServiceClient.clearCart();

        model.addAttribute("order", response);
        model.addAttribute("member", getMemberInfo());
        model.addAttribute("tossClientKey", tossClientKey);

        return "order/order_cart_view";
    }

    @PostMapping("/orders/{order-id}/confirm-order")
    public String requestConfirmOrder(@PathVariable("order-id") Long orderId) {
        orderStatusMemberService.confirmOrder(orderId);
        return "redirect:/order-detail/" + orderId;
    }

    @PostMapping("/orders/{order-id}/cancel-order")
    public String requestCancelOrder(@PathVariable("order-id") long orderId) {
        orderStatusMemberService.cancelOrder(orderId);
        return "redirect:/order-list";
    }

    @PostMapping("/orders/{order-number}/cancel-payment")
    public String requestCancelPayment(@PathVariable("order-number") String orderNumber) {
        orderStatusMemberService.cancelPayment(orderNumber);
        return "redirect:/order-list";
    }

    @PostMapping("/orders/{order-id}/refund-order")
    public String requestRefundOrder(@PathVariable("order-id") Long orderId) {
        orderStatusMemberService.refundOrder(orderId);
        return "redirect:/order-detail/" + orderId;
    }

    protected MemberOrderInfo getMemberInfo() {
        return MemberOrderInfo.fromMemberResponse(memberService.getMember());
    }
}

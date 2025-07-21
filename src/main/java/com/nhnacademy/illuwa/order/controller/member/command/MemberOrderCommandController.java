package com.nhnacademy.illuwa.order.controller.member.command;


import com.nhnacademy.illuwa.cart.client.CartServiceClient;
import com.nhnacademy.illuwa.cart.service.CartService;
import com.nhnacademy.illuwa.member.service.MemberService;
import com.nhnacademy.illuwa.order.dto.command.create.MemberOrderCartRequest;
import com.nhnacademy.illuwa.order.dto.command.create.MemberOrderDirectRequest;
import com.nhnacademy.illuwa.order.dto.query.info.MemberOrderInfo;
import com.nhnacademy.illuwa.order.dto.query.info.OrderCreateResponse;
import com.nhnacademy.illuwa.order.service.MemberOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberOrderCommandController {

    private final MemberOrderService memberOrderService;
    private final MemberService memberService;
    private final CartService cartService;

    @Value("${toss.client-key}")
    private String tossClientKey;

    @PostMapping("/order/order-form/submit-direct")
    public String sendOrder(@ModelAttribute("orderRequest") MemberOrderDirectRequest request, Model model) {

        OrderCreateResponse response = memberOrderService.createDirectOrder(request);
        model.addAttribute("order", response);
        model.addAttribute("member", getMemberInfo());
        model.addAttribute("tossClientKey", tossClientKey);

        return "order/order_view";
    }

    @PostMapping("/order/order-form/submit")
    public String sendOrderByCart(@ModelAttribute("orderRequest") MemberOrderCartRequest request, Model model) {

        OrderCreateResponse response = memberOrderService.createCartOrder(request);
        cartService.clearCart();

        model.addAttribute("order", response);
        model.addAttribute("member", getMemberInfo());
        model.addAttribute("tossClientKey", tossClientKey);

        return "order/order_cart_view";
    }


    protected MemberOrderInfo getMemberInfo() {
        return MemberOrderInfo.fromMemberResponse(memberService.getMember());
    }

}

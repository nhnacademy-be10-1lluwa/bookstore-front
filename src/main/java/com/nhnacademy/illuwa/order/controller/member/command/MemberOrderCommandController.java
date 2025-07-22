package com.nhnacademy.illuwa.order.controller.member.command;


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
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/orders/member")
public class MemberOrderCommandController {

    private final MemberOrderService memberOrderService;
    private final MemberService memberService;
    private final CartService cartService;

    @Value("${toss.client-key}")
    private String tossClientKey;

    // 단일 도서 주문 생성
    @PostMapping
    public String sendOrder(@ModelAttribute("orderRequest") MemberOrderDirectRequest request, Model model) {

        OrderCreateResponse response = memberOrderService.createDirectOrder(request);
        model.addAttribute("order", response);
        model.addAttribute("member", getMemberInfo());
        model.addAttribute("tossClientKey", tossClientKey);

        return "order/order_view";
    }

    // 장바구니 주문 생성
    @PostMapping("/cart")
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

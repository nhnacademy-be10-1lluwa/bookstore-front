package com.nhnacademy.illuwa.order.controller.guest.query;

import com.nhnacademy.illuwa.order.dto.command.create.GuestOrderDirectRequest;
import com.nhnacademy.illuwa.order.dto.query.detail.OrderItemResponse;
import com.nhnacademy.illuwa.order.dto.query.info.GuestOrderInitDirectResponse;
import com.nhnacademy.illuwa.order.service.GuestOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/orders/guest")
public class GuestOrderInitController {

    private final GuestOrderService guestOrderService;

    // 비회원 주문서 페이지
    @GetMapping("/form/{bookId}")
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
}

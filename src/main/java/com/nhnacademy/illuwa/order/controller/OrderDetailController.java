package com.nhnacademy.illuwa.order.controller;

import com.nhnacademy.illuwa.order.dto.query.detail.OrderItemResponseDto;
import com.nhnacademy.illuwa.order.dto.query.detail.OrderResponse;
import com.nhnacademy.illuwa.order.service.MemberOrderService;
import com.nhnacademy.illuwa.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class OrderDetailController {
    private final MemberOrderService memberOrderService;
    private final ReviewService reviewService;

    @GetMapping("/order-detail/{orderId}")
    public String orderOption(@PathVariable("orderId") Long orderId, Model model) {
        OrderResponse orderResponse = memberOrderService.getOrderDetail(orderId);
        List<OrderItemResponseDto> items = orderResponse.getItems();

        List<Long> bookIds = items.stream()
                .map(OrderItemResponseDto::getBookId)
                .collect(Collectors.toList());

        Map<Long, Long> reviewIdMap = reviewService.getExistingReviewIdMap(bookIds);

        Map<Integer, Long> reviewIdIndexMap = new HashMap<>();

        for (int i = 0; i < items.size(); i++) {
            Long bookId = items.get(i).getBookId();
            if (reviewIdMap.containsKey(bookId)) {
                reviewIdIndexMap.put(i, reviewIdMap.get(bookId));
            }
        }
        model.addAttribute("order", orderResponse);
        model.addAttribute("reviewIdIndexMap", reviewIdIndexMap);
        model.addAttribute("activeMenu", "order-list");

        return "order/order_detail";
    }
}

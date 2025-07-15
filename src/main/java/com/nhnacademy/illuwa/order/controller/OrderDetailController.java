package com.nhnacademy.illuwa.order.controller;

import com.nhnacademy.illuwa.order.dto.OrderItemResponseDto;
import com.nhnacademy.illuwa.order.dto.OrderResponse;
import com.nhnacademy.illuwa.order.service.OrderService;
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
    private final OrderService orderService;
    private final ReviewService reviewService;

    @GetMapping("/order-detail/{orderId}")
    public String orderOption(@PathVariable("orderId") Long orderId, Model model) {
        OrderResponse orderResponse = orderService.getOrderDetail(orderId);
        List<OrderItemResponseDto> items = orderResponse.getItems();

        List<Long> bookIds = items.stream()
                .map(OrderItemResponseDto::getBookId)
                .collect(Collectors.toList());

        // 2. 한 번에 리뷰 존재 여부 조회 (N+1 제거)
        Map<Long, Boolean> reviewExistMap = reviewService.areReviewsWritten(bookIds);

        Map<Integer, Boolean> reviewStatusMap = new HashMap<>();
        for (int i = 0; i < items.size(); i++) {
            Long bookId = items.get(i).getBookId();
            Boolean exists = reviewExistMap.getOrDefault(bookId, false);
            reviewStatusMap.put(i, exists);
        }
        model.addAttribute("order", orderResponse);
        model.addAttribute("reviewStatusMap", reviewStatusMap);

        return "order/order_detail";
    }
}
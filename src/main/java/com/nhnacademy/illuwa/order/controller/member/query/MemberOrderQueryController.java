package com.nhnacademy.illuwa.order.controller.member.query;


import com.nhnacademy.illuwa.common.dto.PageResponse;
import com.nhnacademy.illuwa.order.dto.query.detail.OrderItemResponseDto;
import com.nhnacademy.illuwa.order.dto.query.detail.OrderResponse;
import com.nhnacademy.illuwa.order.dto.query.list.OrderListResponse;
import com.nhnacademy.illuwa.order.service.MemberOrderService;
import com.nhnacademy.illuwa.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class MemberOrderQueryController {

    private final MemberOrderService memberOrderService;
    private final ReviewService reviewService;

    @GetMapping("/order-list")
    public String orderList(@RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "10") int size,
                            Model model) {

        PageResponse<OrderListResponse> orderPage = memberOrderService.getOrderHistory(page, size);

        model.addAttribute("orderPage", orderPage);
        model.addAttribute("currentPage", orderPage.page());
        model.addAttribute("totalPages", orderPage.totalPages());
        model.addAttribute("activeMenu", "order-list");

        return "order/order_list";
    }

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

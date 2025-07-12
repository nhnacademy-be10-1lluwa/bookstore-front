package com.nhnacademy.illuwa.order.client;

import com.nhnacademy.illuwa.common.dto.PageResponse;
import com.nhnacademy.illuwa.order.dto.*;
import com.nhnacademy.illuwa.order.dto.admin.OrderUpdateStatusDto;
import com.nhnacademy.illuwa.order.dto.member.MemberOrderDirectRequest;
import com.nhnacademy.illuwa.order.dto.member.MemberOrderInitDirectResponse;
import com.nhnacademy.illuwa.order.dto.types.OrderStatus;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "order-service", url = "${api.base-url}")
public interface OrderServiceClient {
    @GetMapping("/api/packaging")
    List<PackagingResponseDto> getPackaging();

    @PostMapping("/api/packaging")
    PackagingResponseDto createPackaging(PackagingRequestDto packagingRequestDto);

    @GetMapping("/api/order/guest/order-history/{orderNumber}")
    OrderResponse getGuestOrderHistory(@PathVariable("orderNumber") String orderNumber, @RequestParam("contact") String recipientContact);

    @GetMapping("/api/order/member/orders/history")
    PageResponse<OrderListResponse> getMemberOrdersHistory(@RequestParam("page") int page, @RequestParam("size") int size);

    @GetMapping("/api/order/member/orders/{orderId}")
    OrderResponse getMemberOrderHistoryDetail(@PathVariable("orderId") Long orderId);

    @GetMapping("/api/order/member/init-member-info/books/{bookId}")
    MemberOrderInitDirectResponse fetchMemberDirectInfo(@PathVariable("bookId") Long bookId);

    @PostMapping("/api/order/member/submit-direct")
    OrderCreateResponse createMemberDirectOrder(@RequestBody @Valid MemberOrderDirectRequest memberOrderDirectRequest);

    @GetMapping("/api/order/admin/orders")
    PageResponse<OrderListResponse> getOrderStatusPending(@RequestParam("status") OrderStatus status,
                                                          @RequestParam("page") int page,
                                                          @RequestParam("size") int size);

    @GetMapping("/api/order/admin/orders/{orderId}")
    OrderResponse fetchOrderDetail(@PathVariable("orderId") Long orderId);

    @PutMapping("/api/order/admin/orders/{orderId}")
    void updateOrderStatus(@PathVariable("orderId") Long orderId,
                           @RequestBody OrderUpdateStatusDto dto);
}

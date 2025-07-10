package com.nhnacademy.illuwa.order.client;

import com.nhnacademy.illuwa.common.dto.PageResponse;
import com.nhnacademy.illuwa.order.dto.OrderListResponse;
import com.nhnacademy.illuwa.order.dto.OrderResponse;
import com.nhnacademy.illuwa.order.dto.PackagingRequestDto;
import com.nhnacademy.illuwa.order.dto.PackagingResponseDto;
import com.nhnacademy.illuwa.order.dto.admin.OrderUpdateStatusDto;
import com.nhnacademy.illuwa.order.dto.types.OrderStatus;
import jakarta.ws.rs.Path;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "order-service", url = "${api.base-url}")
public interface OrderServiceClient {

    @GetMapping("/api/packaging")
    List<PackagingResponseDto> getPackaging();

    @PostMapping("/api/packaging")
    PackagingResponseDto createPackaging(PackagingRequestDto packagingRequestDto);

    @GetMapping("/order/guest/order-history/{orderNumber}")
    OrderResponse getGuestOrderHistory(@PathVariable("orderNumber") String orderNumber, @RequestParam("contact") String recipientContact);

    @GetMapping("/order/member/orders/history")
    PageResponse<OrderListResponse> getMemberOrdersHistory(@RequestParam("page") int page, @RequestParam("size") int size);

    @GetMapping("/order/member/orders/{orderId}")
    OrderResponse getMemberOrderHistoryDetail(@PathVariable("orderId") Long orderId);

    @GetMapping("/order/admin/orders")
    PageResponse<OrderListResponse> getOrderStatusPending(@RequestParam("status") OrderStatus status,
                                                          @RequestParam("page") int page,
                                                          @RequestParam("size") int size);

    @GetMapping("/order/admin/orders/{orderId}")
    OrderResponse fetchOrderDetail(@PathVariable("orderId") Long orderId);

    @PutMapping("/order/admin/orders/{orderId}")
    void updateOrderStatus(@PathVariable("orderId") Long orderId,
                           @RequestBody OrderUpdateStatusDto dto);

}

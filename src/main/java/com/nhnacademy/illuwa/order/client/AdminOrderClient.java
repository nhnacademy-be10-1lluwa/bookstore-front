package com.nhnacademy.illuwa.order.client;

import com.nhnacademy.illuwa.common.dto.PageResponse;
import com.nhnacademy.illuwa.order.dto.OrderListResponse;
import com.nhnacademy.illuwa.order.dto.OrderResponse;
import com.nhnacademy.illuwa.order.dto.admin.OrderUpdateStatusDto;
import com.nhnacademy.illuwa.order.dto.types.OrderStatus;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "order-service", url = "${api.base-url}", contextId = "adminOrderClient")
public interface AdminOrderClient {

    @GetMapping("/api/order/admin/orders")
    PageResponse<OrderListResponse> listByStatus(@RequestParam OrderStatus status,
                                                 @RequestParam int page,
                                                 @RequestParam int size);

    @GetMapping("/api/order/admin/orders/{orderId}")
    OrderResponse detail(@PathVariable Long orderId);

    @PutMapping("/api/order/admin/orders/{orderId}")
    void updateStatus(@PathVariable Long orderId, @RequestBody @Valid OrderUpdateStatusDto dto);

    // 배치용 엔드포인트
    @GetMapping("/api/order/admin/batch/order-confirmed-update")
    String runOrderConfirmedUpdate();

    @GetMapping("/api/order/admin/batch/member-grade-update")
    String runMemberGradeUpdate();

    @GetMapping("/api/order/admin/batch/order-cleanup")
    String runOrderCleanup();
}

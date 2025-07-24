package com.nhnacademy.illuwa.order.client;

import com.nhnacademy.illuwa.common.dto.PageResponse;
import com.nhnacademy.illuwa.order.dto.query.list.OrderListResponse;
import com.nhnacademy.illuwa.order.dto.query.detail.OrderResponse;
import com.nhnacademy.illuwa.order.dto.command.status.OrderUpdateStatusDto;
import com.nhnacademy.illuwa.order.dto.types.OrderStatus;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "order-service", url = "${api.base-url}", contextId = "adminOrderClient")
public interface AdminOrderClient {

    // 상태별 주문 내역 조회
    @GetMapping("/api/order/admin/orders")
    PageResponse<OrderListResponse> listByStatus(@RequestParam OrderStatus status,
                                                 @RequestParam int page,
                                                 @RequestParam int size);

    // 주문 상세 조회
    @GetMapping("/api/order/admin/orders/{orderId}")
    OrderResponse detail(@PathVariable Long orderId);

    // 주무 상태 변경
    @PutMapping("/api/order/admin/orders/{orderId}")
    void updateStatus(@PathVariable Long orderId, @RequestBody @Valid OrderUpdateStatusDto dto);

    // 배치용 엔드포인트

    // 출고일 기준 10일 지난 주문내역들 구매 확정으로 변경
    @PostMapping("/api/order/admin/batches/orders/confirmations")
    String runOrderConfirmedUpdate();

    // 멤버별 3개월간 순주문 금액 합계 조회
    @PostMapping("/api/order/admin/batches/members/grades")
    String runMemberGradeUpdate();

    // 3일 간 Awaiting_payment 상태인 주문 내역들 삭제
    @PostMapping("/api/order/admin/batches/orders/clean-up")
    String runOrderCleanup();
}

package com.nhnacademy.illuwa.order.client;

import com.nhnacademy.illuwa.order.dto.command.status.ReturnRequestCreateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "order-service", url = "${api.base-url}", contextId = "commonOrderClient")
public interface CommonOrderClient {

    // 주문 확성 - 구매 확정 시, 환불 / 결제 취소 불가
    @PostMapping("/api/order/common/orders/{order-id}/confirm")
    void confirm(@PathVariable("order-id") Long orderId);

    // 주문 취소 - 결제 전 가능
    @PostMapping("/api/order/common/orders/{order-id}/order-cancel")
    void cancelOrder(@PathVariable("order-id") Long orderId);

    // 결제 취소 - 결제 후 ~ 배송 전 가능
    @PostMapping("/api/order/common/orders/{order-number}/payment-cancel")
    void cancelPayment(@PathVariable("order-number") String orderNumber);

    // 주문 환불 - 배송 후 ~ 구매 확정 가능
    @PostMapping("/api/order/common/orders/{order-id}/refund")
    void refund(@PathVariable("order-id") Long orderId, @RequestBody ReturnRequestCreateRequest dto);
}

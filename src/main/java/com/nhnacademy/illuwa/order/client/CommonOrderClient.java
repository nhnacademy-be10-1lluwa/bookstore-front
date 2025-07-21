package com.nhnacademy.illuwa.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "order-service", url = "${api.base-url}", contextId = "commonOrderClient")
public interface CommonOrderClient {

    @PostMapping("/api/order/common/orders/{orderId}/Confirmed")
    void confirm(@PathVariable("orderId") Long orderId);

    @PostMapping("/api/order/common/orders/{order-id}/order-cancel")
    void cancelOrder(@PathVariable("order-id") Long orderId);

    @PostMapping("/api/order/common/orders/{orderNumber}/cancel")
    void cancelPayment(@PathVariable String orderNumber);

    @PostMapping("/api/order/common/orders/{orderId}/refund")
    void refund(@PathVariable Long orderId);
}

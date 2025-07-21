package com.nhnacademy.illuwa.order.client;


import com.nhnacademy.illuwa.order.dto.query.info.OrderCreateResponse;
import com.nhnacademy.illuwa.order.dto.query.detail.OrderResponse;
import com.nhnacademy.illuwa.order.dto.command.create.GuestOrderDirectRequest;
import com.nhnacademy.illuwa.order.dto.query.info.GuestOrderInitDirectResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "order-service", url = "${api.base-url}", contextId = "guestOrderClient")
public interface GuestOrderClient {
    @GetMapping("/api/order/guest/init-guest-info/books/{bookId}")
    GuestOrderInitDirectResponse initDirect(@PathVariable Long bookId);

    @PostMapping("/api/order/guest/submit-direct")
    OrderCreateResponse createDirect(@RequestBody @Valid GuestOrderDirectRequest request);

    @GetMapping("/api/order/guest/order-history/{orderId}")
    OrderResponse getHistory(@PathVariable Long orderId);
}

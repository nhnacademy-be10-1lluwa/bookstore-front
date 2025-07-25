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

    // 비회원 바로 주문하기 내용 조회
    @GetMapping("/api/order/guests/orders/init/books/{book-id}")
    GuestOrderInitDirectResponse initDirect(@PathVariable("book-id") Long bookId);

    // 비회원 바로 주문하기
    @PostMapping("/api/order/guests/orders/direct")
    OrderCreateResponse createDirect(@RequestBody @Valid GuestOrderDirectRequest request);

    // 비회원 주문조회
    @GetMapping("/api/order/guests/orders/{order-id}")
    OrderResponse getHistory(@PathVariable("order-id") Long orderId);
}

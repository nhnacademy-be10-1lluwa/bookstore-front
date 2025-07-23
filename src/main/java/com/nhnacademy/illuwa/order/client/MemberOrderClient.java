package com.nhnacademy.illuwa.order.client;

import com.nhnacademy.illuwa.common.dto.PageResponse;
import com.nhnacademy.illuwa.order.dto.query.info.OrderCreateResponse;
import com.nhnacademy.illuwa.order.dto.query.list.OrderListResponse;
import com.nhnacademy.illuwa.order.dto.query.detail.OrderResponse;
import com.nhnacademy.illuwa.order.dto.command.create.MemberOrderCartRequest;
import com.nhnacademy.illuwa.order.dto.command.create.MemberOrderDirectRequest;
import com.nhnacademy.illuwa.order.dto.query.info.MemberOrderInitDirectResponse;
import com.nhnacademy.illuwa.order.dto.query.info.MemberOrderInitFromCartResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "order-service", url = "${api.base-url}", contextId = "memberOrderClient")
public interface MemberOrderClient {

    // 회원 바로 주문하기 내용 조회
    @GetMapping("/api/order/members/orders/init/books/{book-id}")
    MemberOrderInitDirectResponse initDirect(@PathVariable("book-id") Long bookId);

    // 회원 장바구니 주문하기 내용 조회
    @GetMapping("/api/order/members/orders/init/cart")
    MemberOrderInitFromCartResponse initFromCart();

    // 회원 바로 주문하기
    @PostMapping("/api/order/members/orders/direct")
    OrderCreateResponse createDirect(@RequestBody @Valid MemberOrderDirectRequest request);

    // 회원 장바구니 주문하기
    @PostMapping("/api/order/members/orders/cart")
    OrderCreateResponse createFromCart(@RequestBody @Valid MemberOrderCartRequest request);

    // 회원 주문 내역 조회
    @GetMapping("/api/order/members/orders")
    PageResponse<OrderListResponse> getHistory(@RequestParam int page, @RequestParam int size);

    // 회원 주문 상세 조회
    @GetMapping("/api/order/members/orders/{order-id}")
    OrderResponse getDetail(@PathVariable("order-id") Long orderId);
}

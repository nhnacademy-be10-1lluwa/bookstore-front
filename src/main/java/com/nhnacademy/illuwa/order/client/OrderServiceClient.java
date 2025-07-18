package com.nhnacademy.illuwa.order.client;

import com.nhnacademy.illuwa.common.dto.PageResponse;
import com.nhnacademy.illuwa.order.dto.*;
import com.nhnacademy.illuwa.order.dto.admin.OrderUpdateStatusDto;
import com.nhnacademy.illuwa.order.dto.guest.GuestOrderDirectRequest;
import com.nhnacademy.illuwa.order.dto.guest.GuestOrderInitDirectResponse;
import com.nhnacademy.illuwa.order.dto.guest.OrderGuestCreateResponse;
import com.nhnacademy.illuwa.order.dto.member.MemberOrderCartRequest;
import com.nhnacademy.illuwa.order.dto.member.MemberOrderDirectRequest;
import com.nhnacademy.illuwa.order.dto.member.MemberOrderInitDirectResponse;
import com.nhnacademy.illuwa.order.dto.member.MemberOrderInitFromCartResponse;
import com.nhnacademy.illuwa.order.dto.types.OrderStatus;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "order-service", url = "${api.base-url}", contextId = "orderClientForOrder")
public interface OrderServiceClient {

    // 포장옵션 조회하기
    @GetMapping("/api/packaging")
    List<PackagingResponseDto> getPackaging();

    // 포장옵션 생성하기
    @PostMapping("/api/packaging")
    PackagingResponseDto createPackaging(PackagingRequestDto packagingRequestDto);

    // 비회원 주문 내역 조회하기
    @GetMapping("/api/order/guest/order-history/{orderId}")
    OrderResponse getGuestOrderHistory(@PathVariable("orderId") Long orderId);

    //회원 주문 내역 조회하기
    @GetMapping("/api/order/member/orders/history")
    PageResponse<OrderListResponse> getMemberOrdersHistory(@RequestParam("page") int page, @RequestParam("size") int size);

    // 회원 주문 상세 내역 조회하기
    @GetMapping("/api/order/member/orders/{orderId}")
    OrderResponse getMemberOrderHistoryDetail(@PathVariable("orderId") Long orderId);

    // 회원 바로 주문하기 내용 조회하기
    @GetMapping("/api/order/member/init-member-info/books/{bookId}")
    MemberOrderInitDirectResponse fetchMemberDirectInfo(@PathVariable("bookId") Long bookId);

    // 비회원 바로 주문하기 내용 조회하기
    @GetMapping("/api/order/guest/init-guest-info/books/{bookId}")
    GuestOrderInitDirectResponse fetchGuestDirectInfo(@PathVariable("bookId") Long bookId);

    // 회원 장바구니 주문하기 내용 조회하기
    @GetMapping("/api/order/member/init-from-cart")
    MemberOrderInitFromCartResponse fetchMemberCartInfo();

    // 회원 바로 주문하기
    @PostMapping("/api/order/member/submit-direct")
    OrderCreateResponse createMemberDirectOrder(@RequestBody @Valid MemberOrderDirectRequest memberOrderDirectRequest);

    // 회원 장바구니 주문하기
    @PostMapping("/api/order/member/submit")
    OrderCreateResponse createMemberCartOrder(@RequestBody @Valid MemberOrderCartRequest memberOrderCartRequest);

    // 비회원 바로 주문하기
    @PostMapping("/api/order/guest/submit-direct")
    OrderCreateResponse createGuestDirectOrder(@RequestBody @Valid GuestOrderDirectRequest guestOrderDirectRequest);

    // (어드민) 주문상태별 조회하기
    @GetMapping("/api/order/admin/orders")
    PageResponse<OrderListResponse> getOrderByStatus(@RequestParam("status") OrderStatus status,
                                                     @RequestParam("page") int page,
                                                     @RequestParam("size") int size);

    @GetMapping("/api/order/admin/orders/{orderId}")
    OrderResponse fetchOrderDetail(@PathVariable("orderId") Long orderId);

    @PutMapping("/api/order/admin/orders/{orderId}")
    void updateOrderStatus(@PathVariable("orderId") Long orderId,
                           @RequestBody OrderUpdateStatusDto dto);

    // 주문 취소
    @PostMapping("/api/order/common/orders/{orderNumber}/cancel")
    void cancelOrder(@PathVariable("orderNumber") String orderNumber);

    // 환불
    @PostMapping("/api/order/common/orders/{orderId}/refund")
    void refundOrder(@PathVariable("orderId") Long orderId);


    // 주문 확정 수작업
    @GetMapping("/api/order/admin/batch/order-confirmed-update")
    String runOrderConfirmedUpdate();

    // 회원 등급 업데이트 수작업
    @GetMapping("/api/order/admin/batch/member-grade-update")
    String runMemberGradeUpdate();

    // awaiting 상태 주문 테이블 정리
    @GetMapping("/api/order/admin/batch/order-cleanup")
    String runOrderCleanUp();
}

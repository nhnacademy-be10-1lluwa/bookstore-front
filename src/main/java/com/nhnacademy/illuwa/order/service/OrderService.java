package com.nhnacademy.illuwa.order.service;

import com.nhnacademy.illuwa.common.dto.PageResponse;
import com.nhnacademy.illuwa.order.client.OrderServiceClient;
import com.nhnacademy.illuwa.order.dto.*;
import com.nhnacademy.illuwa.order.dto.admin.OrderUpdateStatusDto;
import com.nhnacademy.illuwa.order.dto.guest.GuestOrderDirectRequest;
import com.nhnacademy.illuwa.order.dto.guest.GuestOrderInitDirectResponse;
import com.nhnacademy.illuwa.order.dto.member.MemberOrderCartRequest;
import com.nhnacademy.illuwa.order.dto.member.MemberOrderDirectRequest;
import com.nhnacademy.illuwa.order.dto.member.MemberOrderInitDirectResponse;
import com.nhnacademy.illuwa.order.dto.member.MemberOrderInitFromCartResponse;
import com.nhnacademy.illuwa.order.dto.types.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderServiceClient orderServiceClient;

    public List<PackagingResponseDto> getPackagingListFromApi() {
        return orderServiceClient.getPackaging();
    }

    // 포장 옵션 등록
    public PackagingResponseDto registerPackaging(PackagingRequestDto packagingRequestDto) {
        return orderServiceClient.createPackaging(packagingRequestDto);
    }

    // 멤버별 주문 내역
    public PageResponse<OrderListResponse> getMemberOrderHistory(@RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size
    ) {
        return orderServiceClient.getMemberOrdersHistory(page, size);
    }

    // 주문 상세 페이지
    public OrderResponse getOrderDetail(Long orderId) {
        return orderServiceClient.getMemberOrderHistoryDetail(orderId);
    }

    // 회원 바로 주문 정보 불러오기
    public MemberOrderInitDirectResponse getMemberInitDataDirect(Long bookId) {
        return orderServiceClient.fetchMemberDirectInfo(bookId);
    }

    // 회원 장바구니 주문 정보 불러오기
    public MemberOrderInitFromCartResponse getMemberInitDataCart() {
        return orderServiceClient.fetchMemberCartInfo();
    }

    // 비회원 바로 주문 정보 불러오기
    public GuestOrderInitDirectResponse getGuestInitDateDirect(Long bookId) {
        return orderServiceClient.fetchGuestDirectInfo(bookId);
    }

    // 회원 바로 주문하기
    public OrderCreateResponse sendDirectOrderMember(MemberOrderDirectRequest request) {
        return orderServiceClient.createMemberDirectOrder(request);
    }

    // 회원 장바구니 주문하기
    public OrderCreateResponse sendCartOrderMember(MemberOrderCartRequest request) {
        return orderServiceClient.createMemberCartOrder(request);
    }

    // 비회원 바로 주문하기
    public OrderCreateResponse sendDirectOrderGuest(GuestOrderDirectRequest request) {
        return orderServiceClient.createGuestDirectOrder(request);
    }

    // Pending 상태 주문 조회
    public PageResponse<OrderListResponse> getOrderStatusPending(@RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size) {
        return orderServiceClient.getOrderByStatus(OrderStatus.Pending, page, size);
    }

    // 어드민 주문 상세 조회
    public OrderResponse getAdminOrderDetail(Long orderId) {
        return orderServiceClient.fetchOrderDetail(orderId);
    }

    // 어드민 주문 상태 변경
    public void updateStatus(Long orderId, OrderStatus status) {
        orderServiceClient.updateOrderStatus(orderId, new OrderUpdateStatusDto(status));
    }

    // 주문 확정 수작업
    public void orderConfirmedUpdate() {
        orderServiceClient.runOrderConfirmedUpdate();
    }

    // 회원 등급 업데이트 수작업
    public void memberGradeUpdate() {
        orderServiceClient.runMemberGradeUpdate();
    }

    // awaiting 상태 주문 테이블 정리
    public void orderCleanUp() {
        orderServiceClient.runOrderCleanUp();
    }

}

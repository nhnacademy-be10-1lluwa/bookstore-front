package com.nhnacademy.illuwa.order.service;

import com.nhnacademy.illuwa.order.dto.OrderCreateResponse;
import com.nhnacademy.illuwa.order.dto.OrderResponse;
import com.nhnacademy.illuwa.order.dto.guest.GuestOrderDirectRequest;
import com.nhnacademy.illuwa.order.dto.guest.GuestOrderInitDirectResponse;

public interface GuestOrderService {

    // 비회원 도서 바로구매에 필요한 기본 정보 불러오기
    GuestOrderInitDirectResponse initDirect(Long bookId);

    // 비회원 도서 바로 주문
    OrderCreateResponse createDirectOrder(GuestOrderDirectRequest request);

    // 비회원 주문 내역 조회
    OrderResponse getOrderHistory(Long orderId);
}

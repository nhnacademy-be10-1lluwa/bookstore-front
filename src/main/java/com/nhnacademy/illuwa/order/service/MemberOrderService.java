package com.nhnacademy.illuwa.order.service;

import com.nhnacademy.illuwa.common.dto.PageResponse;
import com.nhnacademy.illuwa.order.dto.query.info.OrderCreateResponse;
import com.nhnacademy.illuwa.order.dto.query.list.OrderListResponse;
import com.nhnacademy.illuwa.order.dto.query.detail.OrderResponse;
import com.nhnacademy.illuwa.order.dto.command.create.MemberOrderCartRequest;
import com.nhnacademy.illuwa.order.dto.command.create.MemberOrderDirectRequest;
import com.nhnacademy.illuwa.order.dto.query.info.MemberOrderInitDirectResponse;
import com.nhnacademy.illuwa.order.dto.query.info.MemberOrderInitFromCartResponse;

public interface MemberOrderService {

    // 회원 바로 구매에 필요한 기본 정보 불러오기 ( 회원 주소록, 사용 가능한 포인트, 사용 가능한 쿠폰 등 )
    MemberOrderInitDirectResponse initDirect(Long bookId);

    // 회원 장바구니 구매에 필요한 기본 정보 불러오기 ( 회원 주소록, 사용 가능한 포인트, 사용 가능한 쿠폰, 장바구니에 저장된 상품(도서 ID, 수량) 등 )
    MemberOrderInitFromCartResponse initFromCart();

    // 회원 바로 주문
    OrderCreateResponse createDirectOrder(MemberOrderDirectRequest request);

    // 회원 장바구니 주문
    OrderCreateResponse createCartOrder(MemberOrderCartRequest request);

    // 회원 주문 내역 조회
    PageResponse<OrderListResponse> getOrderHistory(int page, int size);

    // 회원 주문 상세 조회
    OrderResponse getOrderDetail(Long orderId);
}

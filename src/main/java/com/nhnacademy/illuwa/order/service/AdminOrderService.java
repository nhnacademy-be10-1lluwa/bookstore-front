package com.nhnacademy.illuwa.order.service;

import com.nhnacademy.illuwa.common.dto.PageResponse;
import com.nhnacademy.illuwa.order.dto.OrderListResponse;
import com.nhnacademy.illuwa.order.dto.OrderResponse;
import com.nhnacademy.illuwa.order.dto.types.OrderStatus;

public interface AdminOrderService {

    // 주문 상태별 주문 조회
    PageResponse<OrderListResponse> listOrdersByStatus(OrderStatus status, int page, int size);

    // 주문 상세 조회
    OrderResponse getOrderDetail(Long orderId);

    // 주문 상태 변경하기
    void updateOrderStatus(Long orderId, OrderStatus orderStatus);

    // 배송일로부터 10일 지난 구매건에 대해 구매 확정 적용하기
    void runOrderConfirmedBatch();

    // 멤버 등급 업데이트 하기
    void runMemberGradeBatch();

    // 주문일로 부터 3일 동안 AwaitingPayment 상태에 머물어 있는 주문건 삭제하기
    void cleanAwaitingOrders();
}

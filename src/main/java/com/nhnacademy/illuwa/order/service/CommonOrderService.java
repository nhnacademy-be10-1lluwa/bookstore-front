package com.nhnacademy.illuwa.order.service;

public interface CommonOrderService {

    // 주문 확정하기 ( 결제 완료 후 가능)
    void confirmOrder(Long orderId);

    // 주문 취소하기 ( 결제 전 가능 )
    void cancelOrder(Long orderId);

    // 결제 취소하기 ( 결제 후, 배송 전 가능 )
    void cancelPayment(String orderNumber);

    // 반품하기 (배송 중 이후로 가능)
    void refundOrder(Long orderId);
}

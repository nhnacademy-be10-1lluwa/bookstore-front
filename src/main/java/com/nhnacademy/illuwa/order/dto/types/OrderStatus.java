package com.nhnacademy.illuwa.order.dto.types;

public enum OrderStatus {
    /**
     * 주문이 생성되었으나 아직 결제가 완료되지 않은 상태.
     */
    AwaitingPayment,

    /**
     * 결제가 정상 승인되어 물류 출고 준비가 완료된 상태.
     */
    Pending,

    /**
     * 택배사에 패키지가 인계되어 운송 중에 있는 상태.
     */
    Shipped,

    /**
     * 고객에게 물품이 성공적으로 도착한 상태.
     */
    Delivered,

    /**
     * 고객이 최종 구매를 확정하거나, 배송 완료 후 자동 확정된 상태.
     */
    Confirmed,

    /**
     * 반품 요청 및 환불이 완료된 상태.
     */
    Returned,

    /**
     * 고객 요청 등으로 주문이 취소된 상태.
     */
    Cancelled
}

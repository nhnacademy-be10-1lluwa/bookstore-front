package com.nhnacademy.illuwa.order.dto.types;

import com.fasterxml.jackson.annotation.JsonValue;

public enum OrderStatus {
    /**
     * 주문이 생성되었으나 아직 결제가 완료되지 않은 상태.
     */
    AwaitingPayment("결제 대기"),

    /**
     * 결제가 정상 승인되어 물류 출고 준비가 완료된 상태.
     */
    Pending("출고 준비 완료"),

    /**
     * 택배사에 패키지가 인계되어 운송 중에 있는 상태.
     */
    Shipped("배송 중"),

    /**
     * 고객에게 물품이 성공적으로 도착한 상태.
     */
    Delivered("배송 완료"),

    /**
     * 고객이 최종 구매를 확정하거나, 배송 완료 후 자동 확정된 상태.
     */
    Confirmed("구매 확정"),

    /**
     * 반품 요청 및 환불이 완료된 상태.
     */
    Returned("반품"),

    /**
     * 고객 요청 등으로 주문이 취소된 상태.
     */
    Cancelled("주문 취소");

    private final String value;

    OrderStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static OrderStatus fromStatus(String status) {
        for (OrderStatus value : OrderStatus.values()) {
            if (value.getValue().equals(status)) {
                return value;
            }
        }
        return null;
    }
}

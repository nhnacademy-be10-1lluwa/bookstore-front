package com.nhnacademy.illuwa.order.dto.types;

import lombok.Getter;

public enum ReturnReason {

    Defective_Item("상품 불량"), // 상품 불량
    Item_Damaged("배송 중 파손"), // 배송 중 파손
    Change_Mind("단순 변심"), // 단순 변심
    Other("기타"); // 기타

    private final String value;

    ReturnReason(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

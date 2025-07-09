package com.nhnacademy.illuwa.coupon.dto.couponPolicy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CouponPolicyResponse {

    private Long id;

    private String code;

    private String discountType;

    private BigDecimal minOrderAmount; // 최소 주문 금액

    private BigDecimal discountAmount; // 할인 금액

    private BigDecimal discountPercent; // 할인 퍼센트

    private BigDecimal maxDiscountAmount; // 최대 할인 금액

    private String status; // 상태 여부

    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}

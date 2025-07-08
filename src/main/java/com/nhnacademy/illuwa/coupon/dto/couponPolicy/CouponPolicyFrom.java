package com.nhnacademy.illuwa.coupon.dto.couponPolicy;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CouponPolicyFrom {

    @NotBlank
    private String code;

    @NotNull
    private BigDecimal minOrderAmount; // 최소 주문 금액

    @NotNull
    private String discountType; // 할인 타입(퍼센트 || 금액)

    private BigDecimal discountAmount; // 할인 금액

    private BigDecimal discountPercent; // 할인 퍼센트

    private BigDecimal maxDiscountAmount; // 최대 할인 금액
}

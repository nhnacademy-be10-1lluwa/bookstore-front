package com.nhnacademy.illuwa.coupon.dto.couponPolicy;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class CouponUpdateResponse {

    private String code;
    private String status;
    private BigDecimal minOrderAmount;
    private BigDecimal discountAmount;
    private BigDecimal discountPercent;
    private BigDecimal maxDiscountAmount;
    private LocalDateTime updatedAt;
}

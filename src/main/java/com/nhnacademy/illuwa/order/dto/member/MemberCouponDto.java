package com.nhnacademy.illuwa.order.dto.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberCouponDto {
    private Long memberCouponId;
    private Long couponId;
    private String couponName;
    private BigDecimal discountAmount;
    private BigDecimal discountPercent;
}

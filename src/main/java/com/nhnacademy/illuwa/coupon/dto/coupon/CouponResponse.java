package com.nhnacademy.illuwa.coupon.dto.coupon;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class CouponResponse {

    private Long id;
    private String couponName;
    private String code;
    private LocalDate validFrom;
    private LocalDate validTo;
    private String couponType;
    private String comment;
    private String conditions;
    private BigDecimal issueCount;
    private Long bookId;
    private Long categoryId;
}

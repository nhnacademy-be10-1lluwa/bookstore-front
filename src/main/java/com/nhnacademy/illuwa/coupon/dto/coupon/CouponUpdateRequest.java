package com.nhnacademy.illuwa.coupon.dto.coupon;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter
public class CouponUpdateRequest {

    private LocalDate validFrom;

    private LocalDate validTo;

    private String comment;

    private BigDecimal issueCount;
}

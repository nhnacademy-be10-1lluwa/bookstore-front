package com.nhnacademy.illuwa.coupon.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberCouponIssueRequest {

    private String couponCode;

    private String couponName;
}

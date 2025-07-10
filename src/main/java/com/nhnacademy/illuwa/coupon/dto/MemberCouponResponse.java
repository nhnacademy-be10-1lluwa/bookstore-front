package com.nhnacademy.illuwa.coupon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberCouponResponse {
    private Long memberCouponId;
    private String memberName;
    private String memberEmail;
    private String couponName;
    private Long couponId;
    private String couponCode;
    private boolean used;
    private LocalDate usedAt;
    private LocalDate issuedAt;
    private LocalDate expiresAt;
}

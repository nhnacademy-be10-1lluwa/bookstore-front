package com.nhnacademy.illuwa.coupon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Objects;

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

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        MemberCouponResponse that = (MemberCouponResponse) object;
        return used == that.used && Objects.equals(memberCouponId, that.memberCouponId) && Objects.equals(memberName, that.memberName) && Objects.equals(memberEmail, that.memberEmail) && Objects.equals(couponName, that.couponName) && Objects.equals(couponId, that.couponId) && Objects.equals(couponCode, that.couponCode) && Objects.equals(usedAt, that.usedAt) && Objects.equals(issuedAt, that.issuedAt) && Objects.equals(expiresAt, that.expiresAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberCouponId, memberName, memberEmail, couponName, couponId, couponCode, used, usedAt, issuedAt, expiresAt);
    }
}

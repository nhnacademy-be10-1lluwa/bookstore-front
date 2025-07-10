package com.nhnacademy.illuwa.coupon.service;

import com.nhnacademy.illuwa.coupon.client.CouponServiceClient;
import com.nhnacademy.illuwa.coupon.dto.MemberCouponIssueRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberCouponService {
    private final CouponServiceClient couponServiceClient;

    public void issueCoupon(MemberCouponIssueRequest request) {
        couponServiceClient.issueCoupon(request);
    }
}

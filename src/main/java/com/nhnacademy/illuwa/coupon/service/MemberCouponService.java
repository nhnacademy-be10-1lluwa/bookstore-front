package com.nhnacademy.illuwa.coupon.service;

import com.nhnacademy.illuwa.coupon.client.CouponServiceClient;
import com.nhnacademy.illuwa.coupon.dto.MemberCouponIssueRequest;
import com.nhnacademy.illuwa.coupon.dto.MemberCouponResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberCouponService {
    private final CouponServiceClient couponServiceClient;

    public void issueCoupon(MemberCouponIssueRequest request) {
        couponServiceClient.issueCoupon(request);
    }

    public List<MemberCouponResponse> getAllMemberCoupons() {
        return couponServiceClient.getAllMemberCoupons();
    }
}

package com.nhnacademy.illuwa.coupon.service;

import com.nhnacademy.illuwa.coupon.client.CouponServiceClient;
import com.nhnacademy.illuwa.coupon.dto.MemberCouponResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CouponService {
    private final CouponServiceClient couponServiceClient;

    public List<MemberCouponResponse> getCoupons(String memberId) {
        return couponServiceClient.getCoupon(memberId);
    }
}

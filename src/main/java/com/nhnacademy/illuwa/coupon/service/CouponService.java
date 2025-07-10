package com.nhnacademy.illuwa.coupon.service;

import com.nhnacademy.illuwa.coupon.client.CouponServiceClient;
import com.nhnacademy.illuwa.coupon.dto.coupon.CouponForm;
import com.nhnacademy.illuwa.coupon.dto.coupon.CouponResponse;
import com.nhnacademy.illuwa.coupon.dto.coupon.CouponUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CouponService {
    private final CouponServiceClient couponServiceClient;

    public CouponResponse createCoupon(CouponForm couponForm) {
        return couponServiceClient.createCoupon(couponForm);
    }

    public List<CouponResponse> getAllCoupons() {
        return couponServiceClient.getCoupon();
    }

    public void deleteCoupon(Long id) {
        couponServiceClient.deleteCoupon(id);
    }

    public CouponResponse updateCoupon(Long id, CouponUpdateRequest request) {
        return couponServiceClient.updateCoupon(id, request);
    }

    public CouponResponse getCouponById(Long id) {
        return couponServiceClient.getCouponById(id);
    }

}

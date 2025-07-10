package com.nhnacademy.illuwa.coupon.service;

import com.nhnacademy.illuwa.coupon.client.CouponServiceClient;
import com.nhnacademy.illuwa.coupon.dto.couponPolicy.CouponPolicyFrom;
import com.nhnacademy.illuwa.coupon.dto.couponPolicy.CouponPolicyResponse;
import com.nhnacademy.illuwa.coupon.dto.MemberCouponResponse;
import com.nhnacademy.illuwa.coupon.dto.couponPolicy.CouponPolicyUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CouponPolicyService {
    private final CouponServiceClient couponServiceClient;

    public List<MemberCouponResponse> getCoupons(String memberId) {
        return couponServiceClient.getCoupon(memberId);
    }

    // 정책 코드로 (수정폼 조회)
    public CouponPolicyResponse getPolicyByCode(String couponPolicyCode) {
        return couponServiceClient.getPolicyByCode(couponPolicyCode);
    }

    // 정책 코드로 수정 (PUT)
    public CouponPolicyResponse updateCouponPolicy(String code, CouponPolicyUpdateRequest req) {
        return couponServiceClient.updateCouponPolicy(code, req);
    }

    // 정책 생성
    public CouponPolicyResponse createCouponPolicy(CouponPolicyFrom couponPolicyFrom) {
        return couponServiceClient.createCouponPolicy(couponPolicyFrom);
    }

    public void deleteCouponPolicy(String code) {
        couponServiceClient.deleteCouponPolicy(code);
    }

    // 정책 목록
    public List<CouponPolicyResponse> getAllPolices() {
        return couponServiceClient.getAllPolicies();
    }
}

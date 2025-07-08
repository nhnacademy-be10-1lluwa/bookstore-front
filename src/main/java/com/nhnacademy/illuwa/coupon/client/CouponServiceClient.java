package com.nhnacademy.illuwa.coupon.client;

import com.nhnacademy.illuwa.coupon.dto.couponPolicy.CouponPolicyFrom;
import com.nhnacademy.illuwa.coupon.dto.couponPolicy.CouponPolicyResponse;
import com.nhnacademy.illuwa.coupon.dto.MemberCouponResponse;
import com.nhnacademy.illuwa.coupon.dto.couponPolicy.CouponPolicyUpdateRequest;
import com.nhnacademy.illuwa.coupon.dto.couponPolicy.CouponUpdateResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@FeignClient(name = "order-service", url = "${api.base-url}", contextId = "orderClientForCoupon")
@FeignClient(name = "order-service", url = "localhost:10305", contextId = "orderClientForCoupon")
public interface CouponServiceClient {

    @GetMapping("/members/member-coupons/{memberEmail}")
    List<MemberCouponResponse> getCoupon(@PathVariable String memberEmail);

    // 정책 생성
    @PostMapping("/admin/coupon-policies")
    CouponPolicyResponse createCouponPolicy(@RequestBody CouponPolicyFrom from);

    // 쿠폰 정책 단건 조회 (CODE)기준 (=수정폼)
    @GetMapping(value = "/admin/coupon-policies", params = "code")
    CouponPolicyResponse getPolicyByCode(@RequestParam("code") String code);

    // 쿠펀 정책 수정 (PUT)
    @PutMapping("/admin/coupon-policies/{code}")
    CouponPolicyResponse updateCouponPolicy(@PathVariable("code") String code, @RequestBody CouponPolicyUpdateRequest request);

    @DeleteMapping("/admin/coupon-policies/{code}")
    void deleteCouponPolicy(@PathVariable String code);
    // 정책 리스트
    @GetMapping("/admin/coupon-policies")
    List<CouponPolicyResponse> getAllPolicies();

}

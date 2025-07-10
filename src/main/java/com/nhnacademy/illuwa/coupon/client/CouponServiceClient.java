package com.nhnacademy.illuwa.coupon.client;

import com.nhnacademy.illuwa.coupon.dto.MemberCouponIssueRequest;
import com.nhnacademy.illuwa.coupon.dto.coupon.CouponForm;
import com.nhnacademy.illuwa.coupon.dto.coupon.CouponResponse;
import com.nhnacademy.illuwa.coupon.dto.coupon.CouponUpdateRequest;
import com.nhnacademy.illuwa.coupon.dto.couponPolicy.CouponPolicyFrom;
import com.nhnacademy.illuwa.coupon.dto.couponPolicy.CouponPolicyResponse;
import com.nhnacademy.illuwa.coupon.dto.MemberCouponResponse;
import com.nhnacademy.illuwa.coupon.dto.couponPolicy.CouponPolicyUpdateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "order-service", url = "${api.base-url}", contextId = "orderClientForCoupon")
//@FeignClient(name = "order-service", url = "localhost:10305", contextId = "orderClientForCoupon")
public interface CouponServiceClient {

    /**
     * 쿠폰 정책 생성 컨트롤러
     */
    @GetMapping("/api/members/member-coupons/{memberEmail}")
    List<MemberCouponResponse> getCoupon(@PathVariable String memberEmail);

    // 정책 생성
    @PostMapping("/api/admin/coupon-policies")
    CouponPolicyResponse createCouponPolicy(@RequestBody CouponPolicyFrom from);

    // 쿠폰 정책 단건 조회 (CODE)기준 (=수정폼)
    @GetMapping(value = "/api/admin/coupon-policies", params = "code")
    CouponPolicyResponse getPolicyByCode(@RequestParam("code") String code);

    // 쿠펀 정책 수정 (PUT)
    @PutMapping("/api/admin/coupon-policies/{code}")
    CouponPolicyResponse updateCouponPolicy(@PathVariable("code") String code, @RequestBody CouponPolicyUpdateRequest request);

    @DeleteMapping("/api/admin/coupon-policies/{code}")
    void deleteCouponPolicy(@PathVariable String code);

    // 정책 리스트
    @GetMapping("/api/admin/coupon-policies")
    List<CouponPolicyResponse> getAllPolicies();

    /**
     * 쿠폰 생성 컨트롤러 (=정책기반)
     */

    // 쿠폰 리스트
    @GetMapping("/api/coupons")
    List<CouponResponse> getCoupon();

    // 쿠폰 등록
    @PostMapping("/api/admin/coupons")
    CouponResponse createCoupon(@RequestBody CouponForm couponForm);

    // 쿠폰 단건 조회 (ID)기준 (=수정폼)
    @GetMapping(value = "/api/admin/coupons/{id}")
    CouponResponse getCouponById(@PathVariable Long id);

    // 쿠펀 수정 수정 (PUT)
    @PutMapping("/api/admin/coupons/{id}")
    CouponResponse updateCoupon(@PathVariable("id") Long id, @RequestBody CouponUpdateRequest request);

    // 쿠폰 삭제
    @DeleteMapping("/api/admin/coupons/{id}")
    void deleteCoupon(@PathVariable Long id);

    /**
     * 사용자 쿠폰 발급
     */
    @PostMapping("/api/member-coupons")
    void issueCoupon(@RequestBody MemberCouponIssueRequest request);

}

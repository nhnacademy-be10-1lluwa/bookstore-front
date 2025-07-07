package com.nhnacademy.illuwa.coupon.client;

import com.nhnacademy.illuwa.coupon.dto.MemberCouponResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "order-service", url = "${api.base-url}", contextId = "orderClientForCoupon")
public interface CouponServiceClient {
    @GetMapping("/members/member-coupons/{memberEmail}")
    List<MemberCouponResponse> getCoupon(@PathVariable String memberEmail);
}

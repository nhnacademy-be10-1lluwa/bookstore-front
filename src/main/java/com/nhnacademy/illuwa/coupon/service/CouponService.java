package com.nhnacademy.illuwa.coupon.service;

import com.nhnacademy.illuwa.coupon.dto.MemberCouponResponse;
import com.nhnacademy.illuwa.order.client.OrderServiceClient;
import com.nhnacademy.illuwa.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CouponService {
    private final OrderServiceClient orderServiceClient;

    public List<MemberCouponResponse> getCoupons(String memberId) {
        return orderServiceClient.getCoupon(memberId);
    }
}

package com.nhnacademy.illuwa.order.dto.member;

import com.nhnacademy.illuwa.cart.dto.CartResponse;
import com.nhnacademy.illuwa.coupon.dto.MemberCouponResponse;
import com.nhnacademy.illuwa.order.dto.PackagingResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberOrderInitFromCartResponse {

    private CartResponse cartResponse;

    private List<MemberAddressDto> recipients;
    private List<MemberCouponResponse> availableCoupons;
    private List<PackagingResponseDto> packaging;
    private BigDecimal pointBalance;

}

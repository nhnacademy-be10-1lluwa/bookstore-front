package com.nhnacademy.illuwa.order.dto.query.info;

import com.nhnacademy.illuwa.cart.dto.CartResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberOrderInitFromCartResponse {

    private CartResponse cartResponse;

    private List<MemberAddressResponse> recipients;
    private Map<Long, List<MemberCouponResponse>> couponMap;
    private List<PackagingResponse> packaging;
    private BigDecimal pointBalance;

}

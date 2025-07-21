package com.nhnacademy.illuwa.order.dto.query.info;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberOrderInitDirectResponse {
    private BookPriceResponse item;
    private List<MemberAddressResponse> recipients;
    private List<MemberCouponResponse> availableCoupons;
    private List<PackagingResponse> packaging;
    private BigDecimal pointBalance;
}

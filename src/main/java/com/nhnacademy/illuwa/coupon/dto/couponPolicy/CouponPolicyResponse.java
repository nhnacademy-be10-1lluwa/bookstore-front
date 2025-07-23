package com.nhnacademy.illuwa.coupon.dto.couponPolicy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponPolicyResponse {

    private Long id;

    private String code;

    private String discountType;

    private BigDecimal minOrderAmount; // 최소 주문 금액

    private BigDecimal discountAmount; // 할인 금액

    private BigDecimal discountPercent; // 할인 퍼센트

    private BigDecimal maxDiscountAmount; // 최대 할인 금액

    private String status; // 상태 여부

    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    public String getMinOrderAmountDisplay() {
        return minOrderAmount == null ? "" : minOrderAmount.stripTrailingZeros().toPlainString();
    }
    public String getDiscountAmountDisplay() {
        return discountAmount == null ? "-" : discountAmount.stripTrailingZeros().toPlainString();
    }
    public String getDiscountPercentDisplay() {
        return discountPercent == null ? "-" : discountPercent.stripTrailingZeros().toPlainString();
    }
    public String getMaxDiscountAmountDisplay() {
        return maxDiscountAmount == null ? "-" : maxDiscountAmount.stripTrailingZeros().toPlainString();
    }

    public String displayString() {
        StringBuilder sb = new StringBuilder();
        // 정책 코드
        sb.append("(").append(code).append(") ");
        // 할인 타입
        sb.append("타입:").append(discountType);

        // 조건별 설명 (null 체크)
        if (minOrderAmount != null) {
            sb.append(" 최소주문금액:").append(minOrderAmount);
        }
        if ("AMOUNT".equals(discountType) && discountAmount != null) {
            sb.append(" 할인금액:").append(discountAmount);
        }
        if ("PERCENT".equals(discountType) && discountPercent != null) {
            sb.append(" 할인퍼센트:").append(discountPercent);
        }
        if ("PERCENT".equals(discountType) && maxDiscountAmount != null) {
            sb.append(" 최대할인금액:").append(maxDiscountAmount);
        }

        return sb.toString();
    }

}

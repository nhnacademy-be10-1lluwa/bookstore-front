package com.nhnacademy.illuwa.coupon.dto.coupon;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CouponForm {

    @NotBlank
    private String couponName; // 쿠폰 이름

    @NotBlank
    private String policyCode; // 정책코드

    @NotNull
    private LocalDate validFrom; // 유효 시작일

    @NotNull
    private LocalDate validTo; // 유효 종료일

    @NotNull
    private String couponType; // 쿠폰 타입

    private String comment; // 쿠폰 설명

    private String conditions; // 사용 조건

    private Long booksId; // 도서 ID

    private Long categoryId; // 카테고리 ID

    @NotNull
    private BigDecimal issueCount; // 발급 수량

}

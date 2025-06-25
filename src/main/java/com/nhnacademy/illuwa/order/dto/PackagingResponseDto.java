package com.nhnacademy.illuwa.order.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class PackagingResponseDto {
    private long packagingId;
    private String packagingName;
    private BigDecimal packagingPrice;
}

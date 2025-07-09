package com.nhnacademy.illuwa.order.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PackagingResponseDto {
    private Long packagingId;
    private String packagingName;
    private BigDecimal packagingPrice;
}

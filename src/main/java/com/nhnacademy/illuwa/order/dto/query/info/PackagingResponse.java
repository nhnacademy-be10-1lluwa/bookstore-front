package com.nhnacademy.illuwa.order.dto.query.info;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PackagingResponse {
    private Long packagingId;
    private String packagingName;
    private BigDecimal packagingPrice;
}

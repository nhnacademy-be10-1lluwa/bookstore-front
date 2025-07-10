package com.nhnacademy.illuwa.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponseDto {
    private Long orderItemId;
    private String title;
    private Long bookId;
    private int quantity;
    private BigDecimal price;
    private Long packagingId;
    private BigDecimal totalPrice;
    private PackagingResponseDto packaging;
}

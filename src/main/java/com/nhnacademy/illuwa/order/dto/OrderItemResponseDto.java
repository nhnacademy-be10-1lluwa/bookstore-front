package com.nhnacademy.illuwa.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponseDto {
    private long orderItemId;
    private String title;
    private long bookId;
    private int quantity;
    private BigDecimal price;
    private long packagingId;
    private BigDecimal totalPrice;
    private PackagingResponseDto packaging;
}

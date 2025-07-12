package com.nhnacademy.illuwa.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
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

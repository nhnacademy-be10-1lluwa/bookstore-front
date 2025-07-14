package com.nhnacademy.illuwa.order.dto.orderRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {
    @JsonProperty("id")
    private Long bookId;
    private Integer quantity;
    private BigDecimal price;
    private Long packagingId;
    private Long couponId;
    private BigDecimal totalPrice;
}

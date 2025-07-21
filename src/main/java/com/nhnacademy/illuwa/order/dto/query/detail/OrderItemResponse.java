package com.nhnacademy.illuwa.order.dto.query.detail;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponse {

    @JsonProperty("id")
    private Long bookId;

    private Integer quantity;

    private BigDecimal price;

    private Long packagingId;

    private Long couponId;

    private BigDecimal totalPrice;
}

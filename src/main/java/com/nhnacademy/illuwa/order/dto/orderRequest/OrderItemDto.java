package com.nhnacademy.illuwa.order.dto.orderRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {
    @NotNull
    @JsonProperty("id")
    private Long bookId;

    @NotNull
    @Positive
    private Integer quantity;

    @NotNull
    @PositiveOrZero
    private BigDecimal price;

    @NotNull
    private Long packagingId;

    private Long couponId;

    @NotNull
    @PositiveOrZero
    private BigDecimal totalPrice;
}

package com.nhnacademy.illuwa.order.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nhnacademy.illuwa.order.dto.types.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateResponse {

    private Long orderId;
    private String orderNumber;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime orderDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate deliveryDate;

    private BigDecimal shippingFee;

    private BigDecimal finalPrice;

    private BigDecimal discountPrice;

    private OrderStatus orderStatus;

    private List<OrderItemResponseDto> items;
}

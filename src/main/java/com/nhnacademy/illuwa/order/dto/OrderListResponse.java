package com.nhnacademy.illuwa.order.dto;

import com.nhnacademy.illuwa.order.dto.orderRequest.OrderItemDto;
import com.nhnacademy.illuwa.order.dto.types.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderListResponse {

    private Long orderId;
    private String orderNumber;
    private LocalDateTime orderDate;
    private BigDecimal totalPrice;
    private OrderStatus orderStatus;
}

package com.nhnacademy.illuwa.order.dto;

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
public class OrderResponse {
    private Long orderId;
    private String orderNumber;
    private Long memberId; // 회원 넘버
    private LocalDateTime orderDate; //
    private LocalDate deliveryDate;
    private BigDecimal shippingFee;
    private BigDecimal totalPrice;
    private OrderStatus orderStatus;
    private List<OrderItemResponseDto> items;
}

package com.nhnacademy.illuwa.order.dto.guest;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nhnacademy.illuwa.order.dto.OrderCreateResponse;
import com.nhnacademy.illuwa.order.dto.OrderItemResponseDto;
import com.nhnacademy.illuwa.order.dto.types.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderGuestCreateResponse {
    private Long orderId;
    private String orderNumber;

    private String name;
    private String email;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime orderDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate deliveryDate;

    private BigDecimal shippingFee;

    private BigDecimal finalPrice;

    private BigDecimal discountPrice;

    private OrderStatus orderStatus;

    private List<OrderItemResponseDto> items;

    public static OrderGuestCreateResponse skrrrr(OrderCreateResponse response, String name, String email) {
        return OrderGuestCreateResponse.builder()
                .orderId(response.getOrderId())
                .orderNumber(response.getOrderNumber())
                .name(name)
                .email(email)
                .orderDate(response.getOrderDate())
                .deliveryDate(response.getDeliveryDate())
                .shippingFee(response.getShippingFee())
                .finalPrice(response.getFinalPrice())
                .discountPrice(response.getDiscountPrice())
                .orderStatus(response.getOrderStatus())
                .items(response.getItems())
                .build();

    }

}

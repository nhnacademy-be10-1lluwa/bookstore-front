package com.nhnacademy.illuwa.order.dto.member;

import com.nhnacademy.illuwa.order.dto.orderRequest.OrderItemDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberOrderCartRequest {
    private LocalDate deliveryDate;
    private String recipientName;
    private String recipientContact;
    private String postCode;
    private String readAddress;
    private String detailAddress;

    private List<OrderItemDto> cartItems;
    private BigDecimal usedPoint;
    private Long memberCouponId;
}

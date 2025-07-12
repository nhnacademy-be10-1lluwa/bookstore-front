package com.nhnacademy.illuwa.order.dto.member;

import com.nhnacademy.illuwa.order.dto.orderRequest.OrderItemDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberOrderDirectRequest {
    private LocalDate deliveryDate;
    private String recipientName;
    private String recipientContact;
    private String postCode;
    private String readAddress;
    private String detailAddress;
    private OrderItemDto item;
    private Long memberCouponId;
    private BigDecimal usedPoint;
}

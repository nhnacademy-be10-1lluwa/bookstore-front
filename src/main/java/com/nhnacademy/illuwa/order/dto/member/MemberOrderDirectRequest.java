package com.nhnacademy.illuwa.order.dto.member;

import com.nhnacademy.illuwa.order.dto.orderRequest.OrderItemDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberOrderDirectRequest {
    @NotNull
    private LocalDate deliveryDate;
    @NotBlank
    private String recipientName;
    @NotBlank
    private String recipientContact;
    @NotBlank
    private String postCode;
    @NotBlank
    private String readAddress;
    @NotBlank
    private String detailAddress;
    @NotNull
    @Valid
    private OrderItemDto item;
    private Long memberCouponId;
    @NotNull
    @PositiveOrZero
    private BigDecimal usedPoint;
}

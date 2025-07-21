package com.nhnacademy.illuwa.order.dto.command.create;

import com.nhnacademy.illuwa.order.dto.query.detail.OrderItemResponse;
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
    private OrderItemResponse item;
    private Long memberCouponId;
    @NotNull
    @PositiveOrZero
    private BigDecimal usedPoint;
}

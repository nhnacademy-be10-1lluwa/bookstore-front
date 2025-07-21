package com.nhnacademy.illuwa.order.dto.command.create;

import com.nhnacademy.illuwa.order.dto.query.detail.OrderItemResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberOrderCartRequest {
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

    @NotEmpty
    @Valid
    private List<OrderItemResponse> cartItems;

    @NotNull
    @PositiveOrZero
    private BigDecimal usedPoint;

    private Long memberCouponId;
}

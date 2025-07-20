package com.nhnacademy.illuwa.order.dto.command.create;

import com.nhnacademy.illuwa.order.dto.query.detail.OrderItemResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuestOrderDirectRequest {
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

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String contact;
}
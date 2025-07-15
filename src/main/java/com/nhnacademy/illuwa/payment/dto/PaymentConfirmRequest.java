package com.nhnacademy.illuwa.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentConfirmRequest {
    private String paymentKey;
    private String orderId;
    private int amount;
}

package com.nhnacademy.illuwa.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuestLoginRequest {
    private String orderNumber;
    private String recipientContact;
}

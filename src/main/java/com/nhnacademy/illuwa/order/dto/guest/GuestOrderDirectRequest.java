package com.nhnacademy.illuwa.order.dto.guest;

import com.nhnacademy.illuwa.order.dto.orderRequest.OrderItemDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuestOrderDirectRequest {
    private LocalDate deliveryDate;
    private String recipientName;
    private String recipientContact;
    private String postCode;
    private String readAddress;
    private String detailAddress;
    private OrderItemDto item;

    private String name;
    private String orderPassword;
    private String email;
    private String contact;
}

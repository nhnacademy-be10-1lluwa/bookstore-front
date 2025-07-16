package com.nhnacademy.illuwa.guest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GuestResponse {
    String guestId;
    Long orderId;
    String orderNumber;
    String name;
    String email;
    String contact;
}

package com.nhnacademy.illuwa.order.dto.admin;

import com.nhnacademy.illuwa.order.dto.types.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderUpdateStatusDto {
    private OrderStatus orderStatus;
}

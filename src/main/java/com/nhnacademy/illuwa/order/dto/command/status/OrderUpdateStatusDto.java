package com.nhnacademy.illuwa.order.dto.command.status;

import com.nhnacademy.illuwa.order.dto.types.OrderStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Valid
@NoArgsConstructor
@AllArgsConstructor
public class OrderUpdateStatusDto {

    @NotNull
    private OrderStatus orderStatus;
}

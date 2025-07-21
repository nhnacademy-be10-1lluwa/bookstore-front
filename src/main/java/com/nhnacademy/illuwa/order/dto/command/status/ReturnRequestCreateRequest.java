package com.nhnacademy.illuwa.order.dto.command.status;

import com.nhnacademy.illuwa.order.dto.types.ReturnReason;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReturnRequestCreateRequest {

    private LocalDateTime requestedAt;
    private ReturnReason returnReason;
}

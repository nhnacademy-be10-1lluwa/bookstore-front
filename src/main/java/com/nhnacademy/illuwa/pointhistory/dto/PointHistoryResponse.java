package com.nhnacademy.illuwa.pointhistory.dto;

import com.nhnacademy.illuwa.pointhistory.enums.PointHistoryType;
import com.nhnacademy.illuwa.pointhistory.enums.PointReason;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PointHistoryResponse {
    private PointHistoryType type;
    private PointReason reason;
    private BigDecimal amount;
    private BigDecimal balance;
    private LocalDateTime createdAt;
}

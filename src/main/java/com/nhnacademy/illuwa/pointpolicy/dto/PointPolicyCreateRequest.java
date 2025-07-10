package com.nhnacademy.illuwa.pointpolicy.dto;

import com.nhnacademy.illuwa.pointpolicy.enums.PointValueType;
import com.nhnacademy.illuwa.pointpolicy.enums.PolicyStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointPolicyCreateRequest {
    @NotBlank(message = "포인트 정책 키 설정은 필수입니다.")
    private String policyKey;

    @Builder.Default
    private PolicyStatus status = PolicyStatus.ACTIVE;

    @NotNull(message = "포인트 값은 필수입니다.")
    private BigDecimal value;

    @NotNull(message = "포인트 타입은 RATE/AMOUNT 필수입니다.")
    private PointValueType valueType;

    private String description;
}

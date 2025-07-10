package com.nhnacademy.illuwa.pointpolicy.dto;

import com.nhnacademy.illuwa.pointpolicy.enums.PointValueType;
import com.nhnacademy.illuwa.pointpolicy.enums.PolicyStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PointPolicyResponse {
    private String policyKey;

    private PolicyStatus status;

    private BigDecimal value;

    private PointValueType valueType;

    private String description;

}

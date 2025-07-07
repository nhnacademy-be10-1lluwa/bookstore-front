package com.nhnacademy.illuwa.memberaddress.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberAddressLimitResponse {
    boolean canAdd;
    int currentCount;
    int maxLimit;
}

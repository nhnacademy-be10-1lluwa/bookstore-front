package com.nhnacademy.illuwa.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressUpdateRequest {
    private String addressName;
    private String recipient;
    private String recipientPhone;
    private String addressDetail;
    private boolean isDefault;
}

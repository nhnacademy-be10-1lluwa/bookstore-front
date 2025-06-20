package com.nhnacademy.illuwa.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequest {
    private String addressName;
    private String recipient;
    private String contact;
    private String addressDetail;
    private boolean isDefault;
}

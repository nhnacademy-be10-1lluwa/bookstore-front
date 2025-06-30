package com.nhnacademy.illuwa.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponse {
    private Long addressId;
    private String addressName;
    private String recipient;
    private String contact;
    private String addressDetail;
    private boolean isDefault;
}

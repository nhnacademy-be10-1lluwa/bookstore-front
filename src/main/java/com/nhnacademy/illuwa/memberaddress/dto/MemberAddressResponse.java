package com.nhnacademy.illuwa.memberaddress.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
public class MemberAddressResponse {
    private long memberAddressId;
    private long memberId;
    private String addressName;
    private String recipientName;
    private String recipientContact;
    private String postCode;
    private String address;
    private String detailAddress;
    private boolean defaultAddress;
    @Builder.Default
    @JsonProperty("forcedDefaultAddress")
    private boolean forcedDefaultAddress = false;
}

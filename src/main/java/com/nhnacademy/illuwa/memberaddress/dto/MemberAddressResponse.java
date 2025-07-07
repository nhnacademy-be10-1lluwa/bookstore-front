package com.nhnacademy.illuwa.memberaddress.dto;

import lombok.*;

@Data
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
}

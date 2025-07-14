package com.nhnacademy.illuwa.auth.dto;

import com.nhnacademy.illuwa.member.enums.Status;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InactiveCheckResponse {
    private Status status;
    private String name;
    private String email;
    private String contact;
}

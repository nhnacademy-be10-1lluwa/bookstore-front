package com.nhnacademy.illuwa.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nhnacademy.illuwa.member.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberLoginResponse {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("expires_in")
    private long expiresIn;
    private Status status;
}

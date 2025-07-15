package com.nhnacademy.illuwa.auth.dto.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerifyCodeResponse {
    boolean success;
    Long memberId;
    String email;
    String message;
}

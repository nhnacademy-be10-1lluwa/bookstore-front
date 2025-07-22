package com.nhnacademy.illuwa.auth.dto.message;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendVerificationRequest {
    String contact;
}

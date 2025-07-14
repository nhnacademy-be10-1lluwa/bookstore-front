package com.nhnacademy.illuwa.auth.dto.message;

import jakarta.validation.constraints.Email;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendVerificationRequest {
    @Email(message = "유효한 이메일 형식이 아닙니다")
    String email;
}

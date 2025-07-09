package com.nhnacademy.illuwa.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpdateRequest {
    @NotBlank(message = "이름은 공백이 아니어야 합니다")
    private String name;

    @NotBlank
    private String currentPassword;

    private String password;

    @Pattern(regexp = "^010-\\d{3,4}-\\d{4}$",
            message = "휴대폰 번호는 010으로 시작하는 11자리 숫자와 '-'로 구성되어야 합니다.")
    private String contact;
}
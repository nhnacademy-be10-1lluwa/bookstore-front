package com.nhnacademy.illuwa.dto.member;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberUpdateRequest {
    private String name;
    @Email
    private String email;
    private String password;
    private String phone;
}

package com.nhnacademy.illuwa.dto.member;

import com.nhnacademy.illuwa.enums.Role;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberRegisterRequest {
    private String name;
    private String birth;
    @Email
    private String email;
    private String password;
    private Role role;
    private String phoneNumber;
}

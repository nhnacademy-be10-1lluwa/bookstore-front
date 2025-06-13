package com.nhnacademy.illuwa.dto.member;

import com.nhnacademy.illuwa.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberRegisterRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String birth;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하이여야 합니다.")
    private String password;
    @NotBlank
    private Role role;
    @NotBlank
    private String phoneNumber;

    public MemberRegisterRequest(String name, String birth, String email, String password, String phoneNumber) {
        this.name = name;
        this.birth = birth;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = Role.USER;
    }
}

package com.nhnacademy.illuwa.auth.dto;

import com.nhnacademy.illuwa.user.enums.Role;
import com.nhnacademy.illuwa.user.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponse {
    private Long memberId;
    private String name;
    private String email;
    private Role role;
    private String contact;
    private String gradeName;
    private BigDecimal point;
    private Status status;
    private LocalDateTime lastLoginAt;
}

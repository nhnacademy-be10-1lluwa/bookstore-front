package com.nhnacademy.illuwa.member.dto;

import com.nhnacademy.illuwa.member.enums.Role;
import com.nhnacademy.illuwa.member.enums.Status;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberResponse {
    private long memberId;
    private String paycoId;
    private String name;
    private LocalDate birth;
    private String email;
    private Role role;
    private String contact;
    private String gradeName;
    private BigDecimal point;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;
}

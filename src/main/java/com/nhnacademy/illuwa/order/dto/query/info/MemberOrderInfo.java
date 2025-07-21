package com.nhnacademy.illuwa.order.dto.query.info;


import com.nhnacademy.illuwa.member.dto.MemberResponse;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberOrderInfo {
    @NotBlank
    private String name;
    @NotBlank
    @Email
    private String email;

    public static MemberOrderInfo fromMemberResponse(MemberResponse dto) {
        return MemberOrderInfo.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .build();
    }
}

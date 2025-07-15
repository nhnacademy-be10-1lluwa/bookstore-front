package com.nhnacademy.illuwa.order.dto.orderRequest;


import com.nhnacademy.illuwa.member.dto.MemberResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberOrderInfo {
    private String name;
    private String email;

    public static MemberOrderInfo fromMemberResponse(MemberResponse dto) {
        return MemberOrderInfo.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .build();
    }
}

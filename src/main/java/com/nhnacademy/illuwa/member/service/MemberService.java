package com.nhnacademy.illuwa.member.service;

import com.nhnacademy.illuwa.member.client.MemberServiceClient;
import com.nhnacademy.illuwa.member.dto.MemberResponse;
import com.nhnacademy.illuwa.member.dto.MemberUpdateRequest;
import com.nhnacademy.illuwa.member.dto.PasswordCheckRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberServiceClient memberServiceClient;

    public MemberResponse getMember() {
        return memberServiceClient.getMember();
    }

    public MemberResponse updateMember(@Valid @RequestBody MemberUpdateRequest request){
        return memberServiceClient.updateMember(request);
    }

    public void deleteMember(){
        memberServiceClient.deleteMember();
    }

    public boolean checkPassword(@RequestBody PasswordCheckRequest request){
        return memberServiceClient.checkPassword(request);
    }
}

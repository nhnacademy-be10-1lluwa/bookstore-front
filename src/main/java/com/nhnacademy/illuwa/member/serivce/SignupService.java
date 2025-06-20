package com.nhnacademy.illuwa.member.serivce;

import com.nhnacademy.illuwa.member.dto.MemberRegisterRequest;

public interface SignupService {
    public void sendSignup(MemberRegisterRequest memberRegisterRequest);
}

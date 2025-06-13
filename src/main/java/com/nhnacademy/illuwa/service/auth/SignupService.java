package com.nhnacademy.illuwa.service.auth;

import com.nhnacademy.illuwa.dto.member.MemberRegisterRequest;

public interface SignupService {
    public void sendSignup(MemberRegisterRequest memberRegisterRequest);
}

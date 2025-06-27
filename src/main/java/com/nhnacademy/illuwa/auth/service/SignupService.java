package com.nhnacademy.illuwa.auth.service;

import com.nhnacademy.illuwa.auth.dto.MemberRegisterRequest;

public interface SignupService {
    public void sendSignup(MemberRegisterRequest memberRegisterRequest);
}

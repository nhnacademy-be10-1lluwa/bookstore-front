package com.nhnacademy.illuwa.service.auth;

import com.nhnacademy.illuwa.dto.auth.MemberLoginRequest;
import com.nhnacademy.illuwa.dto.member.MemberResponse;

public interface LoginService {
    public MemberResponse sendLogin(MemberLoginRequest memberLoginRequest);
}

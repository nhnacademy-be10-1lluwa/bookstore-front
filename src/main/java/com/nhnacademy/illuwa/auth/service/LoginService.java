package com.nhnacademy.illuwa.auth.service;

import com.nhnacademy.illuwa.auth.dto.MemberLoginRequest;
import com.nhnacademy.illuwa.auth.dto.MemberResponse;

public interface LoginService {
    public MemberResponse sendLogin(MemberLoginRequest memberLoginRequest);
}

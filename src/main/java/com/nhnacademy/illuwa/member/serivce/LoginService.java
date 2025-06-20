package com.nhnacademy.illuwa.member.serivce;

import com.nhnacademy.illuwa.member.dto.MemberLoginRequest;
import com.nhnacademy.illuwa.member.dto.MemberResponse;

public interface LoginService {
    public MemberResponse sendLogin(MemberLoginRequest memberLoginRequest);
}

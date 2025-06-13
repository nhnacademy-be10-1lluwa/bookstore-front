package com.nhnacademy.illuwa.service.auth;

import com.nhnacademy.illuwa.dto.auth.MemberLoginRequest;

public interface LoginService {
    public void sendLogin(MemberLoginRequest memberLoginRequest);
}

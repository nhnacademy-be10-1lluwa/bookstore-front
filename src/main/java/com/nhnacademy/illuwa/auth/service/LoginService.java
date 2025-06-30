package com.nhnacademy.illuwa.auth.service;

import com.nhnacademy.illuwa.auth.client.UserServiceClient;
import com.nhnacademy.illuwa.auth.dto.MemberResponse;
import com.nhnacademy.illuwa.auth.dto.MemberLoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginService {
    private final UserServiceClient userServiceClient;

    public MemberResponse getMember(Long memberId) {
        return userServiceClient.getUser(memberId);
    }

    public MemberResponse login(MemberLoginRequest loginRequest) {
        return userServiceClient.sendLogin(loginRequest);
    }
}

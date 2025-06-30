package com.nhnacademy.illuwa.auth.service;

import com.nhnacademy.illuwa.auth.client.UserServiceClient;
import com.nhnacademy.illuwa.auth.dto.MemberRegisterRequest;
import com.nhnacademy.illuwa.auth.dto.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SignupService {
    private final UserServiceClient userServiceClient;

    public MemberResponse signup(MemberRegisterRequest memberRegisterRequest) {
        return userServiceClient.sendSignup(memberRegisterRequest);
    }
}

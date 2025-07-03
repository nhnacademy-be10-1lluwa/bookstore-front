package com.nhnacademy.illuwa.auth.client;

import com.nhnacademy.illuwa.auth.dto.MemberLoginRequest;
import com.nhnacademy.illuwa.auth.dto.MemberRegisterRequest;
import com.nhnacademy.illuwa.auth.dto.TokenResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "auth-service", url = "${api.base-url}")
public interface AuthClient {

    @PostMapping("/auth/signup")
    void signup(@Valid MemberRegisterRequest memberRegisterRequest);

    @PostMapping("/auth/login")
    TokenResponse login(@Valid MemberLoginRequest memberLoginRequest);
}

package com.nhnacademy.illuwa.auth.client;

import com.nhnacademy.illuwa.auth.dto.*;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "auth-service", url = "${api.base-url}")
public interface AuthClient {

    @PostMapping("/api/auth/signup")
    void signup(@Valid MemberRegisterRequest memberRegisterRequest);

    @PostMapping("/api/auth/login")
    TokenResponse login(@Valid MemberLoginRequest memberLoginRequest);

    @PostMapping("/api/auth/social-login")
    TokenResponse socialLogin(@RequestBody SocialLoginRequest socialLoginRequest);
}

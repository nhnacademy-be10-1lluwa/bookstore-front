package com.nhnacademy.illuwa.auth.client;

import com.nhnacademy.illuwa.auth.dto.MemberLoginRequest;
import com.nhnacademy.illuwa.auth.dto.MemberRegisterRequest;
import com.nhnacademy.illuwa.auth.dto.TokenResponse;
import com.nhnacademy.illuwa.auth.dto.UserSession;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "auth-service", url = "${api.base-url}")
public interface AuthClient {

    @PostMapping("/auth/signup")
    void signup(@Valid MemberRegisterRequest memberRegisterRequest);

    @PostMapping("/auth/login")
    TokenResponse login(@Valid MemberLoginRequest memberLoginRequest);

    @PostMapping("/auth/parse-token")
    UserSession parseToken(@RequestHeader("Authorization") String token);
}

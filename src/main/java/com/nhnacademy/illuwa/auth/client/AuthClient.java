package com.nhnacademy.illuwa.auth.client;

import com.nhnacademy.illuwa.auth.dto.MemberRegisterRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "auth-service", url = "${api.base-url}")
public interface AuthClient {

    @PostMapping("/auth/signup")
    void signup(MemberRegisterRequest memberRegisterRequest);
}

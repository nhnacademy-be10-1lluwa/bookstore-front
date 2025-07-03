package com.nhnacademy.illuwa.user.client;

import com.nhnacademy.illuwa.auth.dto.MemberLoginRequest;
import com.nhnacademy.illuwa.auth.dto.MemberRegisterRequest;
import com.nhnacademy.illuwa.auth.dto.MemberResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service", url = "${api.base-url}", contextId = "userClientForAuth")
public interface UserAuthServiceClient {
    @PostMapping("/members")
    MemberResponse sendSignup(@RequestBody MemberRegisterRequest memberRegisterRequest);

    @PostMapping("/members/login")
    MemberResponse sendLogin(@RequestBody MemberLoginRequest memberLoginRequest);
}

package com.nhnacademy.illuwa.user.client;

import com.nhnacademy.illuwa.auth.dto.MemberLoginRequest;
import com.nhnacademy.illuwa.auth.dto.MemberRegisterRequest;
import com.nhnacademy.illuwa.auth.dto.MemberResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service", url = "${api.base-url}", contextId = "userClientForMember")
public interface UserServiceClient {
    @GetMapping("/members/{memberId}")
    MemberResponse getUser(@PathVariable("memberId") Long memberId);

    @PostMapping("/members/login")
    MemberResponse sendLogin(@RequestBody MemberLoginRequest memberLoginRequest);

    @PostMapping("/members")
    MemberResponse sendSignup(@RequestBody MemberRegisterRequest memberRegisterRequest);
}

package com.nhnacademy.illuwa.user.member.client;

import com.nhnacademy.illuwa.auth.dto.MemberResponse;
import com.nhnacademy.illuwa.auth.dto.MemberUpdateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "user-service", url = "${api.base-url}", contextId = "userClientForMember")
public interface MemberServiceClient {

    // 회원 단일 조회 (X-USER_ID 기반)
    @GetMapping("/members")
    MemberResponse getMember();

    // 회원 단일 조회 (이메일 기반)
    @GetMapping(value = "/members", params = "memberEmail")
    MemberResponse getMemberByEmail(@RequestParam("memberEmail") String email);

    // 회원 수정
    @PatchMapping("/members")
    MemberResponse updateMember(@RequestBody MemberUpdateRequest request);
    // 회원 삭제
    @DeleteMapping("/members")
    void deleteMember();
}

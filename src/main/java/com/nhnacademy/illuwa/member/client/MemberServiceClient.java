package com.nhnacademy.illuwa.member.client;

import com.nhnacademy.illuwa.member.dto.MemberResponse;
import com.nhnacademy.illuwa.member.dto.MemberUpdateRequest;
import com.nhnacademy.illuwa.member.dto.PasswordCheckRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "user-service", url = "${api.base-url}", contextId = "userClientForMember")
public interface MemberServiceClient {

    // 회원 단일 조회 (X-USER_ID 기반)
    @GetMapping("/api/members")
    MemberResponse getMember();

    // 회원 수정
    @PutMapping("/api/members")
    MemberResponse updateMember(@RequestBody MemberUpdateRequest request);

    // 회원 삭제
    @DeleteMapping("/api/members")
    void deleteMember();

    @PostMapping("/api/members/check-pw")
    boolean checkPassword(@RequestBody PasswordCheckRequest request);

}

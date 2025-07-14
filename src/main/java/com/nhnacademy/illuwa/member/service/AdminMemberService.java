package com.nhnacademy.illuwa.member.service;

import com.nhnacademy.illuwa.common.dto.PageResponse;
import com.nhnacademy.illuwa.member.client.AdminMemberServiceClient;
import com.nhnacademy.illuwa.member.dto.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Service
public class AdminMemberService {
    private final AdminMemberServiceClient adminMemberServiceClient;

    public PageResponse<MemberResponse> getPagedMemberList(@RequestParam("page") int page, @RequestParam("size") int size) {
        return adminMemberServiceClient.getPagedMemberList(page, size);
    }
}

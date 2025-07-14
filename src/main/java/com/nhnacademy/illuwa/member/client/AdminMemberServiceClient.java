package com.nhnacademy.illuwa.member.client;

import com.nhnacademy.illuwa.common.dto.PageResponse;
import com.nhnacademy.illuwa.member.dto.MemberResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service", url = "${api.base-url}", contextId = "userClientForAdmin")
public interface AdminMemberServiceClient {
    @GetMapping("/api/admin/members/paged")
    PageResponse<MemberResponse> getPagedMemberList(@RequestParam("page") int page, @RequestParam("size") int size);
}

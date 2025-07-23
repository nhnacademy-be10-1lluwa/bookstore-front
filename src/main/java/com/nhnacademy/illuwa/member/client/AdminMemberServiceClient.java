package com.nhnacademy.illuwa.member.client;

import com.nhnacademy.illuwa.common.dto.PageResponse;
import com.nhnacademy.illuwa.member.enums.GradeName;
import com.nhnacademy.illuwa.member.dto.MemberResponse;
import com.nhnacademy.illuwa.pointhistory.dto.PointHistoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;


@FeignClient(name = "user-service", url = "${api.base-url}", contextId = "userClientForAdmin")
public interface AdminMemberServiceClient {
    @GetMapping("/api/admin/members")
    PageResponse<MemberResponse> getPagedMemberList(@RequestParam(value = "grade", required = false) GradeName gradeName, @RequestParam("page") int page, @RequestParam("size") int size);

    @PostMapping("/api/members/grades/{gradeName}/points")
    List<PointHistoryResponse> givePointToGrade(@PathVariable GradeName gradeName, @RequestParam(value = "point") BigDecimal point);
}
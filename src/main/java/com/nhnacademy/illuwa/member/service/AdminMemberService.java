package com.nhnacademy.illuwa.member.service;

import com.nhnacademy.illuwa.common.dto.PageResponse;
import com.nhnacademy.illuwa.grade.enums.GradeName;
import com.nhnacademy.illuwa.member.client.AdminMemberServiceClient;
import com.nhnacademy.illuwa.member.dto.MemberResponse;
import com.nhnacademy.illuwa.pointhistory.dto.PointHistoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminMemberService {
    private final AdminMemberServiceClient adminMemberServiceClient;

    public PageResponse<MemberResponse> getPagedMemberList(@RequestParam(value = "grade", required = false) GradeName gradeName, @RequestParam("page") int page, @RequestParam("size") int size) {
        return adminMemberServiceClient.getPagedMemberListFilteredByGrade(gradeName, page, size);
    }

    public List<PointHistoryResponse> givePointToGrade(@RequestParam(value = "grade", required = false) GradeName gradeName, @RequestParam(value = "point", required = false) BigDecimal point){
       return adminMemberServiceClient.givePointToGrade(gradeName, point);
    }
}

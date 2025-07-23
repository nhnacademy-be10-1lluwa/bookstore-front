package com.nhnacademy.illuwa.member.service;

import com.nhnacademy.illuwa.common.dto.PageResponse;
import com.nhnacademy.illuwa.member.enums.GradeName;
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

    public PageResponse<MemberResponse> getPagedMemberList(GradeName gradeName, int page, int size) {
        return adminMemberServiceClient.getPagedMemberList(gradeName, page, size);
    }

    public List<PointHistoryResponse> givePointToGrade(GradeName gradeName, BigDecimal point){
       return adminMemberServiceClient.givePointToGrade(gradeName, point);
    }
}

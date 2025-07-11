package com.nhnacademy.illuwa.pointhistory.service;

import com.nhnacademy.illuwa.memberaddress.dto.PageResponse;
import com.nhnacademy.illuwa.pointhistory.client.PointHistoryServiceClient;
import com.nhnacademy.illuwa.pointhistory.dto.PointHistoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PointHistoryService {
    private final PointHistoryServiceClient pointHistoryServiceClient;

    public BigDecimal getMemberPoint(){
        return pointHistoryServiceClient.getMemberPoint();
    }

    public List<PointHistoryResponse> getMemberPointHistoryList(){
        return pointHistoryServiceClient.getMemberPointHistoryList();
    }

    public PageResponse<PointHistoryResponse> getPagedMemberPointHistoryList(String type, int page, int size) {
        return pointHistoryServiceClient.getPagedMemberPointHistoryList(type, page, size);
    }
}

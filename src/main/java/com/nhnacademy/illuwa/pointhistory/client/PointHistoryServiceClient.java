package com.nhnacademy.illuwa.pointhistory.client;

import com.nhnacademy.illuwa.memberaddress.dto.PageResponse;
import com.nhnacademy.illuwa.pointhistory.dto.PointHistoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@FeignClient(name = "user-service", url = "${api.base-url}", contextId = "UserClientForPointHistory")
public interface PointHistoryServiceClient {

    @GetMapping("/api/members/points")
    BigDecimal getMemberPoint();

    @GetMapping("/api/members/points/histories")
    List<PointHistoryResponse> getMemberPointHistoryList();

    @GetMapping("/api/members/points/histories/paged")
    PageResponse<PointHistoryResponse> getPagedMemberPointHistoryList(@RequestParam("type") String type, @RequestParam("page") int page, @RequestParam("size") int size);
}

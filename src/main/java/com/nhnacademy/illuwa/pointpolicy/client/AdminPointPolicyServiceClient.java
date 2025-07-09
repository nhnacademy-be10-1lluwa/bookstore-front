package com.nhnacademy.illuwa.pointpolicy.client;

import com.nhnacademy.illuwa.pointpolicy.dto.PointPolicyCreateRequest;
import com.nhnacademy.illuwa.pointpolicy.dto.PointPolicyResponse;
import com.nhnacademy.illuwa.pointpolicy.dto.PointPolicyUpdateRequest;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "user-service", url = "${api.base-url}", contextId = "userClientForPointPolicy")
public interface AdminPointPolicyServiceClient {
    @GetMapping("/admin/point-policies")
    List<PointPolicyResponse> getPointPolicyList();

    @PostMapping("/admin/point-policies")
    PointPolicyResponse createPointPolicy(@Valid @RequestBody PointPolicyCreateRequest request);

    @GetMapping("/admin/point-policies/{policyKey}")
    PointPolicyResponse getPointPolicy(@PathVariable("policyKey") String policyKey);

    @PutMapping("/admin/point-policies/{policyKey}")
    PointPolicyResponse updatePointPolicy(@PathVariable("policyKey") String policyKey,
                                          @RequestBody PointPolicyUpdateRequest request);

    @DeleteMapping("/admin/point-policies/{policyKey}")
    void deletePointPolicy(@PathVariable("policyKey") String policyKey);
}

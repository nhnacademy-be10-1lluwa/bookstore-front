package com.nhnacademy.illuwa.pointpolicy.service;

import com.nhnacademy.illuwa.pointpolicy.client.AdminPointPolicyServiceClient;
import com.nhnacademy.illuwa.pointpolicy.dto.PointPolicyCreateRequest;
import com.nhnacademy.illuwa.pointpolicy.dto.PointPolicyResponse;
import com.nhnacademy.illuwa.pointpolicy.dto.PointPolicyUpdateRequest;
import com.nhnacademy.illuwa.pointpolicy.enums.PolicyStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AdminPointPolicyService {

    private final AdminPointPolicyServiceClient client;

    public List<PointPolicyResponse> getPointPolicyList() {
        return client.getPointPolicyList().stream()
                .sorted(Comparator.comparing((PointPolicyResponse p) -> p.getStatus().equals(PolicyStatus.INACTIVE)))
                .collect(Collectors.toList());
    }

    public PointPolicyResponse getPointPolicy(String policyKey) {
        return client.getPointPolicy(policyKey);
    }

    public PointPolicyCreateRequest prepareCreateForm() {
        return PointPolicyCreateRequest.builder().build();
    }

    public PointPolicyUpdateRequest prepareUpdateForm(String policyKey) {
        PointPolicyResponse response = getPointPolicy(policyKey);
        return PointPolicyUpdateRequest.builder()
                .value(response.getValue())
                .valueType(response.getValueType())
                .description(response.getDescription())
                .status(response.getStatus())
                .build();
    }

    public PointPolicyUpdateRequest prepareViewForm(String policyKey) {
        return prepareUpdateForm(policyKey);
    }

    public PointPolicyResponse createPolicy(PointPolicyCreateRequest request) {
        return client.createPointPolicy(request);
    }

    public PointPolicyResponse updatePolicy(String policyKey, PointPolicyUpdateRequest request) {
        return client.updatePointPolicy(policyKey, request);
    }

    public void deletePolicy(String policyKey) {
        client.deletePointPolicy(policyKey);
    }
}

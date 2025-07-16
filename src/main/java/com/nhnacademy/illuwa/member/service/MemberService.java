package com.nhnacademy.illuwa.member.service;

import com.nhnacademy.illuwa.common.exception.BackendApiException;
import com.nhnacademy.illuwa.member.client.MemberServiceClient;
import com.nhnacademy.illuwa.member.dto.MemberResponse;
import com.nhnacademy.illuwa.member.dto.MemberUpdateRequest;
import com.nhnacademy.illuwa.member.dto.PasswordCheckRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberServiceClient memberServiceClient;

    public MemberResponse getMember() {
        return memberServiceClient.getMember();
    }

    public MemberResponse updateMember(@Valid MemberUpdateRequest request) {
        if (!StringUtils.hasText(request.getName()) || !StringUtils.hasText(request.getCurrentPassword())) {
            throw new BackendApiException("필수 입력값을 모두 채워주세요!", "REQUIRED_FIELDS_MISSING", 400);
        }

        if (StringUtils.hasText(request.getPassword())) {
            String pattern = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}";
            if (!request.getPassword().matches(pattern)) {
                throw new BackendApiException("비밀번호는 8~16자 영문+숫자+특수문자 형식이어야 합니다.", "INVALID_PASSWORD_FORMAT", 400);
            }
        } else if ("".equals(request.getPassword())) {
            request.setPassword(null); // 빈 문자열이면 null 처리
        }

        if (StringUtils.hasText(request.getContact())) {
            String contactPattern = "^010-\\d{3,4}-\\d{4}$";
            if (!request.getContact().matches(contactPattern)) {
                throw new BackendApiException("연락처 형식이 올바르지 않습니다.", "INVALID_CONTACT_FORMAT", 400);
            }
        }

        if (!checkPassword(new PasswordCheckRequest(request.getCurrentPassword()))) {
            throw new BackendApiException("현재 비밀번호가 일치하지 않습니다.", "INVALID_CURRENT_PASSWORD", 401);
        }
        return memberServiceClient.updateMember(request);
    }


    public void deleteMember(){
        memberServiceClient.deleteMember();
    }

    public boolean checkPassword(@RequestBody PasswordCheckRequest request){
        return memberServiceClient.checkPassword(request);
    }

    public Map<Long, String> getMemberNameFromReviewers(@RequestBody List<Long> reviewerIds) {
        return memberServiceClient.getMemberNameFromReviewers(reviewerIds);
    }
}

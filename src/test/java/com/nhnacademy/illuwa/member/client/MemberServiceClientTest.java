package com.nhnacademy.illuwa.member.client;

import com.nhnacademy.illuwa.auth.dto.payco.PaycoMemberUpdateRequest;
import com.nhnacademy.illuwa.member.dto.MemberResponse;
import com.nhnacademy.illuwa.member.dto.MemberUpdateRequest;
import com.nhnacademy.illuwa.member.dto.PasswordCheckRequest;
import com.nhnacademy.illuwa.member.enums.Role;
import com.nhnacademy.illuwa.member.enums.Status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class MemberServiceClientTest {

    @Mock
    private MemberServiceClient memberServiceClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private MemberResponse createMockMemberResponse() {
        return MemberResponse.builder()
                .memberId(1L)
                .paycoId("payco123")
                .name("홍길동")
                .birth(LocalDate.of(1990, 1, 1))
                .email("hong@sample.com")
                .role(Role.USER)
                .contact("010-1234-5678")
                .gradeName("일반")
                .point(BigDecimal.valueOf(10000))
                .status(Status.ACTIVE)
                .createdAt(LocalDateTime.now().minusYears(1))
                .lastLoginAt(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("회원 단일 조회 테스트")
    void testGetMember() {
        MemberResponse mockResponse = createMockMemberResponse();
        Mockito.when(memberServiceClient.getMember()).thenReturn(mockResponse);

        MemberResponse response = memberServiceClient.getMember();

        assertNotNull(response);
        assertEquals(mockResponse.getMemberId(), response.getMemberId());
        assertEquals(mockResponse.getName(), response.getName());
        assertEquals(mockResponse.getEmail(), response.getEmail());
    }

    @Test
    @DisplayName("회원 수정 테스트")
    void testUpdateMember() {
        MemberUpdateRequest updateRequest = MemberUpdateRequest.builder()
                .name("홍길동")
                .currentPassword("currentPass123")
                .password("newPass123")
                .contact("010-1234-5678")
                .build();

        MemberResponse mockResponse = createMockMemberResponse();
        Mockito.when(memberServiceClient.updateMember(any(MemberUpdateRequest.class))).thenReturn(mockResponse);

        MemberResponse response = memberServiceClient.updateMember(updateRequest);

        assertNotNull(response);
        assertEquals(mockResponse.getMemberId(), response.getMemberId());
        assertEquals(mockResponse.getName(), response.getName());
    }

    @Test
    @DisplayName("회원 삭제 테스트")
    void testDeleteMember() {
        Mockito.doNothing().when(memberServiceClient).deleteMember();

        assertDoesNotThrow(() -> memberServiceClient.deleteMember());

        Mockito.verify(memberServiceClient, Mockito.times(1)).deleteMember();
    }

    @Test
    @DisplayName("비밀번호 확인 테스트")
    void testCheckPassword() {
        PasswordCheckRequest pwRequest = PasswordCheckRequest.builder()
                .inputPassword("password123")
                .build();

        Mockito.when(memberServiceClient.checkPassword(any(PasswordCheckRequest.class))).thenReturn(true);

        boolean result = memberServiceClient.checkPassword(pwRequest);

        assertTrue(result);
    }

    @Test
    @DisplayName("페이코 회원 초기 정보 설정 테스트")
    void testUpdatePaycoMember() {
        PaycoMemberUpdateRequest paycoRequest = new PaycoMemberUpdateRequest();
        paycoRequest.setName("홍길동");
        paycoRequest.setBirth(LocalDate.of(1990, 1, 1));
        paycoRequest.setEmail("hong@sample.com");
        paycoRequest.setContact("010-1234-5678");

        Mockito.doNothing().when(memberServiceClient).updatePaycoMember(any(PaycoMemberUpdateRequest.class));

        assertDoesNotThrow(() -> memberServiceClient.updatePaycoMember(paycoRequest));

        Mockito.verify(memberServiceClient, Mockito.times(1)).updatePaycoMember(paycoRequest);
    }
}



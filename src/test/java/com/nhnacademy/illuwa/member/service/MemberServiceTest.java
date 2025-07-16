package com.nhnacademy.illuwa.member.service;

import com.nhnacademy.illuwa.member.client.MemberServiceClient;
import com.nhnacademy.illuwa.member.dto.MemberResponse;
import com.nhnacademy.illuwa.member.dto.MemberUpdateRequest;
import com.nhnacademy.illuwa.member.dto.PasswordCheckRequest;
import com.nhnacademy.illuwa.member.enums.Role;
import com.nhnacademy.illuwa.member.enums.Status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @Mock
    MemberServiceClient memberServiceClient;

    @InjectMocks
    MemberService memberService;

    @Test
    @DisplayName("getMember() 테스트")
    void testGetMember() {
        MemberResponse expected = MemberResponse.builder()
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

        when(memberServiceClient.getMember()).thenReturn(expected);
        MemberResponse actual = memberService.getMember();

        assertEquals(expected, actual);
        verify(memberServiceClient, times(1)).getMember();
    }

    @Test
    @DisplayName("updateMember 정상 동작 테스트")
    void testUpdateMember() {
        MemberUpdateRequest request = MemberUpdateRequest.builder()
                .name("홍길동")
                .currentPassword("currentPass123")
                .password("TestPass123!")
                .contact("010-1234-5678")
                .build();

        MemberResponse expected = MemberResponse.builder()
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

        when(memberServiceClient.updateMember(any(MemberUpdateRequest.class))).thenReturn(expected);

        MemberService spyService = Mockito.spy(new MemberService(memberServiceClient));
        doReturn(true).when(spyService).checkPassword(any(PasswordCheckRequest.class));

        MemberResponse actual = spyService.updateMember(request);

        assertEquals(expected, actual);
        verify(memberServiceClient, times(1)).updateMember(request);
    }

    @Test
    @DisplayName("deleteMember 정상 동작 테스트")
    void testDeleteMember() {
        doNothing().when(memberServiceClient).deleteMember();

        assertDoesNotThrow(() -> memberService.deleteMember());
        verify(memberServiceClient, times(1)).deleteMember();
    }

    @Test
    @DisplayName("checkPassword 정상 동작 테스트")
    void testCheckPassword() {
        PasswordCheckRequest pwRequest = PasswordCheckRequest.builder()
                .inputPassword("password123")
                .build();

        when(memberServiceClient.checkPassword(any(PasswordCheckRequest.class))).thenReturn(true);

        boolean result = memberService.checkPassword(pwRequest);

        assertTrue(result);
        verify(memberServiceClient, times(1)).checkPassword(pwRequest);
    }

}

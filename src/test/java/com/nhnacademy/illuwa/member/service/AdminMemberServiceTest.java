package com.nhnacademy.illuwa.member.service;

import com.nhnacademy.illuwa.common.dto.PageResponse;
import com.nhnacademy.illuwa.member.enums.GradeName;
import com.nhnacademy.illuwa.member.client.AdminMemberServiceClient;
import com.nhnacademy.illuwa.member.dto.MemberResponse;
import com.nhnacademy.illuwa.member.enums.Role;
import com.nhnacademy.illuwa.member.enums.Status;
import com.nhnacademy.illuwa.pointhistory.dto.PointHistoryResponse;
import com.nhnacademy.illuwa.pointhistory.enums.PointHistoryType;
import com.nhnacademy.illuwa.pointhistory.enums.PointReason;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminMemberServiceTest {
    @Mock
    private AdminMemberServiceClient adminMemberServiceClient;

    @InjectMocks
    private AdminMemberService adminMemberService;

    @Test
    @DisplayName("getPagedMemberList 정상 동작 테스트")
    void testGetPagedMemberList() {
        // given
        List<MemberResponse> memberList = List.of(
                MemberResponse.builder()
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
                        .build(),
                MemberResponse.builder()
                        .memberId(2L)
                        .paycoId("payco456")
                        .name("임꺽정")
                        .birth(LocalDate.of(1990, 2, 2))
                        .email("imggukjung@sample.com")
                        .role(Role.USER)
                        .contact("010-5678-1234")
                        .gradeName("일반")
                        .point(BigDecimal.valueOf(10000))
                        .status(Status.ACTIVE)
                        .createdAt(LocalDateTime.now().minusYears(1))
                        .lastLoginAt(LocalDateTime.now())
                        .build()
        );

        PageResponse<MemberResponse> expected = new PageResponse<>(
                memberList,
                5,
                50L,
                1,
                10,
                false,
                true
        );

        when(adminMemberService.getPagedMemberList(any(), anyInt(), anyInt())).thenReturn(expected);

        // when
        PageResponse<MemberResponse> actual = adminMemberService.getPagedMemberList(GradeName.BASIC, 1, 10);

        // then
        assertEquals(expected, actual);
        assertEquals(2, actual.content().size());
        assertEquals("홍길동", actual.content().getFirst().getName());
        verify(adminMemberServiceClient, times(1)).getPagedMemberListFilteredByGrade(GradeName.BASIC, 1, 10);
    }

    @Test
    @DisplayName("givePointToGradeService 정상 작동 테스트")
    void testGivePointToGradeService() {
        // given
        List<PointHistoryResponse> mockResponse = List.of(
                new PointHistoryResponse(
                        1L,
                        PointHistoryType.EARN,
                        PointReason.JOIN,
                        BigDecimal.valueOf(1000),
                        BigDecimal.valueOf(5000),
                        LocalDateTime.now()
                ),
                new PointHistoryResponse(
                        2L,
                        PointHistoryType.DEDUCT,
                        PointReason.PURCHASE,
                        BigDecimal.valueOf(500),
                        BigDecimal.valueOf(5500),
                        LocalDateTime.now()
                )
        );

        when(adminMemberServiceClient.givePointToGrade(eq(GradeName.BASIC), eq(BigDecimal.valueOf(1500))))
                .thenReturn(mockResponse);

        // when
        List<PointHistoryResponse> response = adminMemberService.givePointToGrade(GradeName.BASIC, BigDecimal.valueOf(1500));

        // then
        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals(PointHistoryType.EARN, response.getFirst().getType());
        assertEquals(PointReason.JOIN, response.getFirst().getReason());
        assertEquals(BigDecimal.valueOf(1000), response.getFirst().getAmount());
        assertEquals(BigDecimal.valueOf(5000), response.getFirst().getBalance());
        assertNotNull(response.getFirst().getCreatedAt());
    }
}

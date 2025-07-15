package com.nhnacademy.illuwa.member.client;

import com.nhnacademy.illuwa.common.dto.PageResponse;
import com.nhnacademy.illuwa.grade.enums.GradeName;
import com.nhnacademy.illuwa.member.dto.MemberResponse;
import com.nhnacademy.illuwa.member.enums.Role;
import com.nhnacademy.illuwa.member.enums.Status;
import com.nhnacademy.illuwa.pointhistory.dto.PointHistoryResponse;
import com.nhnacademy.illuwa.pointhistory.enums.PointHistoryType;
import com.nhnacademy.illuwa.pointhistory.enums.PointReason;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class AdminMemberServiceClientTest {

    @Mock
    private AdminMemberServiceClient adminMemberServiceClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    @DisplayName("회원 페이지 목록 조회 테스트")
    void testGetPagedMemberList() {
        PageResponse<MemberResponse> mockPageResponse = new PageResponse<>(
                List.of(
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
                                .paycoId("payco123")
                                .name("임꺽정")
                                .birth(LocalDate.of(1990, 2, 2))
                                .email("imggukjung@sample.com")
                                .role(Role.USER)
                                .contact("010-1234-5678")
                                .gradeName("골드")
                                .point(BigDecimal.valueOf(10000))
                                .status(Status.ACTIVE)
                                .createdAt(LocalDateTime.now().minusYears(1))
                                .lastLoginAt(LocalDateTime.now())
                                .build()
                ),
                5,
                50L,
                1,
                10,
                false,
                true
        );

        when(adminMemberServiceClient.getPagedMemberListFilteredByGrade(eq(GradeName.BASIC), eq(0), eq(10)))
                .thenReturn(mockPageResponse);

        // then
        PageResponse<MemberResponse> response = adminMemberServiceClient.getPagedMemberListFilteredByGrade(GradeName.BASIC, 0, 10);

        assertNotNull(response);
        assertEquals(2, response.content().size());
        assertEquals("홍길동", response.content().getFirst().getName());
        assertEquals(5, response.totalPages());
        assertEquals(50L, response.totalElements());
        assertEquals(1, response.page());
        assertEquals(10, response.size());
        assertTrue(response.first());
        assertFalse(response.last());

    }

    @Test
    @DisplayName("등급별 포인트 지급 테스트")
    void testGivePointToGrade() {
        // given
        List<PointHistoryResponse> mockResponse = List.of(
                new PointHistoryResponse(
                        1L,
                        PointHistoryType.EARN,
                        PointReason.GRADE_EVENT,
                        BigDecimal.valueOf(1000),
                        BigDecimal.valueOf(6000),
                        LocalDateTime.now()
                ),
                new PointHistoryResponse(
                        2L,
                        PointHistoryType.EARN,
                        PointReason.GRADE_EVENT,
                        BigDecimal.valueOf(1000),
                        BigDecimal.valueOf(6000),
                        LocalDateTime.now()
                )
        );

        when(adminMemberServiceClient.givePointToGrade(eq(GradeName.BASIC), eq(BigDecimal.valueOf(1500))))
                .thenReturn(mockResponse);

        // when
        List<PointHistoryResponse> response = adminMemberServiceClient.givePointToGrade(GradeName.BASIC, BigDecimal.valueOf(1500));

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
package com.nhnacademy.illuwa.member.service;

import com.nhnacademy.illuwa.common.dto.PageResponse;
import com.nhnacademy.illuwa.member.client.AdminMemberServiceClient;
import com.nhnacademy.illuwa.member.dto.MemberResponse;
import com.nhnacademy.illuwa.member.enums.Role;
import com.nhnacademy.illuwa.member.enums.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AdminMemberServiceTest {
    @Mock
    private AdminMemberServiceClient adminMemberServiceClient;

    @InjectMocks
    private AdminMemberService adminMemberService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

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
                5,      // totalPages
                50L,    // totalElements
                1,      // page
                10,     // size
                false,  // last
                true    // first
        );

        when(adminMemberServiceClient.getPagedMemberList(1, 10)).thenReturn(expected);

        // when
        PageResponse<MemberResponse> actual = adminMemberService.getPagedMemberList(1, 10);

        // then
        assertEquals(expected, actual);
        assertEquals(2, actual.content().size());
        assertEquals("홍길동", actual.content().get(0).getName());
        verify(adminMemberServiceClient, times(1)).getPagedMemberList(1, 10);
    }
}

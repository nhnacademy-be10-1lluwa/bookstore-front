package com.nhnacademy.illuwa.member.client;

import com.nhnacademy.illuwa.common.dto.PageResponse;
import com.nhnacademy.illuwa.member.dto.MemberResponse;
import com.nhnacademy.illuwa.member.enums.Role;
import com.nhnacademy.illuwa.member.enums.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;


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
        PageResponse<MemberResponse> mockPageResponse = new PageResponse<MemberResponse>(
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
                                .gradeName("일반")
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

        List<MemberResponse> expected = List.of(
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
                        .gradeName("일반")
                        .point(BigDecimal.valueOf(10000))
                        .status(Status.ACTIVE)
                        .createdAt(LocalDateTime.now().minusYears(1))
                        .lastLoginAt(LocalDateTime.now())
                        .build()
        );

        Mockito.when(adminMemberServiceClient.getPagedMemberList(anyInt(), anyInt()))
                .thenReturn(mockPageResponse);

        PageResponse<MemberResponse> response = adminMemberServiceClient.getPagedMemberList(1, 10);
        assertNotNull(response);
        assertEquals(mockPageResponse, response);
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).getMemberId(), response.content().get(i).getMemberId());
        }
        assertEquals(5, response.totalPages());
        assertEquals(50, response.totalElements());
        assertEquals(10, response.size());
        assertEquals(1,  response.page());
        assertTrue(response.first());
        assertFalse(response.last());

    }

}
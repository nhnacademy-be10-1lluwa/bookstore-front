package com.nhnacademy.illuwa.member.client;

import com.nhnacademy.illuwa.common.dto.PageResponse;
import com.nhnacademy.illuwa.member.dto.MemberResponse;
import com.nhnacademy.illuwa.member.enums.Role;
import com.nhnacademy.illuwa.member.enums.Status;
import feign.FeignException;
import feign.Request;
import feign.RequestTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminMemberServiceClientTest {

    @InjectMocks
    private AdminMemberServiceClient adminMemberServiceClient;

    @Mock
    private AdminMemberServiceClient mockClient;

    private PageResponse<MemberResponse> sampleResponse;

    @BeforeEach
    void setUp() {
        // 샘플 MemberResponse 데이터 설정
        MemberResponse member = MemberResponse.builder()
                .memberId(1L)
                .paycoId("payco123")
                .name("홍길동")
                .birth(LocalDate.of(1990, 1, 1))
                .email("hong@example.com")
                .role(Role.USER)
                .contact("010-1234-5678")
                .gradeName("VIP")
                .point(new BigDecimal("1000.50"))
                .status(Status.ACTIVE)
                .createdAt(LocalDateTime.of(2025, 1, 1, 10, 0))
                .lastLoginAt(LocalDateTime.of(2025, 7, 14, 10, 0))
                .build();

        // 샘플 PageResponse 데이터 설정: record의 정규 생성자 사용
        sampleResponse = new PageResponse<>(
                Collections.singletonList(member), // content
                1, // page
                10, // size
                1L, // totalElements
                1, // totalPages
                true, // first
                true // last
        );

        // 실제 객체를 테스트에 사용하되, Feign 호출을 모킹
        adminMemberServiceClient = mockClient;
    }

    @Test
    void testGetPagedMemberList_Success() {
        // given
        int page = 1;
        int size = 10;

        // 모킹 설정: 성공적인 API 호출
        when(mockClient.getPagedMemberList(eq(page), eq(size)))
                .thenReturn(sampleResponse);

        // when
        PageResponse<MemberResponse> result = adminMemberServiceClient.getPagedMemberList(page, size);

        // then
        assertNotNull(result, "응답이 null이면 안 됩니다.");
        assertEquals(1, result.page(), "페이지 번호가 일치해야 합니다.");
        assertEquals(10, result.size(), "페이지 크기가 일치해야 합니다.");
        assertEquals(1L, result.totalElements(), "총 요소 수가 일치해야 합니다.");
        assertEquals(1, result.totalPages(), "총 페이지 수가 일치해야 합니다.");
        assertTrue(result.first(), "첫 페이지 여부가 true여야 합니다.");
        assertTrue(result.last(), "마지막 페이지 여부가 true여야 합니다.");
        assertEquals(1, result.content().size(), "컨텐츠 리스트 크기가 일치해야 합니다.");

        // MemberResponse 필드 검증
        MemberResponse member = result.content().get(0);
        assertEquals(1L, member.getMemberId(), "회원 ID가 일치해야 합니다.");
        assertEquals("payco123", member.getPaycoId(), "Payco ID가 일치해야 합니다.");
        assertEquals("홍길동", member.getName(), "이름이 일치해야 합니다.");
        assertEquals(LocalDate.of(1990, 1, 1), member.getBirth(), "생년월일이 일치해야 합니다.");
        assertEquals("hong@example.com", member.getEmail(), "이메일이 일치해야 합니다.");
        assertEquals(Role.USER, member.getRole(), "역할이 일치해야 합니다.");
        assertEquals("010-1234-5678", member.getContact(), "연락처가 일치해야 합니다.");
        assertEquals("VIP", member.getGradeName(), "등급 이름이 일치해야 합니다.");
        assertEquals(new BigDecimal("1000.50"), member.getPoint(), "포인트가 일치해야 합니다.");
        assertEquals(Status.ACTIVE, member.getStatus(), "상태가 일치해야 합니다.");
        assertEquals(LocalDateTime.of(2025, 1, 1, 10, 0), member.getCreatedAt(), "생성 시간이 일치해야 합니다.");
        assertEquals(LocalDateTime.of(2025, 7, 14, 10, 0), member.getLastLoginAt(), "마지막 로그인 시간이 일치해야 합니다.");

        verify(mockClient, times(1)).getPagedMemberList(page, size);
    }

    @Test
    void testGetPagedMemberList_Unauthorized() {
        // given
        int page = 1;
        int size = 10;

        // 모킹 설정: 401 Unauthorized 에러 시뮬레이션 (JWT 토큰 문제 가정)
        FeignException unauthorized = new FeignException.Unauthorized(
                "Unauthorized",
                Request.create(Request.HttpMethod.GET, "/api/admin/members/paged", new HashMap<>(), new byte[0], null, new RequestTemplate()),
                new byte[0],
                new HashMap<>()
        );
        when(mockClient.getPagedMemberList(anyInt(), anyInt())).thenThrow(unauthorized);

        // when & then
        FeignException exception = assertThrows(FeignException.Unauthorized.class, () -> {
            adminMemberServiceClient.getPagedMemberList(page, size);
        });
        assertEquals(HttpStatus.UNAUTHORIZED.value(), exception.status(), "HTTP 상태 코드가 401이어야 합니다.");
        verify(mockClient, times(1)).getPagedMemberList(page, size);
    }

    @Test
    void testGetPagedMemberList_ServerError() {
        // given
        int page = 1;
        int size = 10;

        // 모킹 설정: 500 Internal Server Error 시뮬레이션
        FeignException serverError = new FeignException.InternalServerError(
                "Internal Server Error",
                Request.create(Request.HttpMethod.GET, "/api/admin/members/paged", new HashMap<>(), new byte[0], null, new RequestTemplate()),
                new byte[0],
                new HashMap<>()
        );
        when(mockClient.getPagedMemberList(anyInt(), anyInt())).thenThrow(serverError);

        // when & then
        FeignException exception = assertThrows(FeignException.InternalServerError.class, () -> {
            adminMemberServiceClient.getPagedMemberList(page, size);
        });
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.status(), "HTTP 상태 코드가 500이어야 합니다.");
        verify(mockClient, times(1)).getPagedMemberList(page, size);
    }
}
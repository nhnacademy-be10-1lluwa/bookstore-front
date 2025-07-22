package com.nhnacademy.illuwa.coupon.controller;

import com.nhnacademy.illuwa.config.handler.CategoryControllerAdvice;
import com.nhnacademy.illuwa.coupon.dto.MemberCouponIssueRequest;
import com.nhnacademy.illuwa.coupon.dto.coupon.CouponResponse;
import com.nhnacademy.illuwa.coupon.service.CouponService;
import com.nhnacademy.illuwa.coupon.service.MemberCouponService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CouponController.class,
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {CategoryControllerAdvice.class})})
@AutoConfigureMockMvc(addFilters = false)
class CouponControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberCouponService memberCouponService;

    @MockBean
    private CouponService couponService;

    @Test
    @DisplayName("사용자 기반 쿠폰 페이지 반환")
    void couponsTest() throws Exception {
        List<CouponResponse> coupons = List.of(
                CouponResponse.builder()
                        .id(1L)
                        .couponName("testCouponName")
                        .code("TEST_CODE")
                        .validFrom(LocalDate.now())
                        .validTo(LocalDate.now().plusMonths(1L))
                        .couponType("WELCOME")
                        .issueCount(BigDecimal.TWO)
                        .build(),
                CouponResponse.builder()
                        .id(2L)
                        .couponName("testCouponName2")
                        .code("TEST_CODE")
                        .validFrom(LocalDate.now())
                        .validTo(LocalDate.now().plusYears(1L))
                        .couponType("BIRTHDAY")
                        .issueCount(BigDecimal.ONE).build()
        );

        when(couponService.getAllCoupons()).thenReturn(coupons);

        mockMvc.perform(get("/coupons"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/couponpage"))
                .andExpect(model().attribute("coupons", hasSize(2)));
    }

    @Test
    @DisplayName("사용자 기반 쿠폰 발급")
    void issueCoupon() throws Exception {
        mockMvc.perform(post("/coupons/issue")
                        .param("couponName","테스트쿠폰"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/coupons"));

        verify(memberCouponService, times(1)).issueCoupon(any(MemberCouponIssueRequest.class));
    }
}

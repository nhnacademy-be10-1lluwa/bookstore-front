package com.nhnacademy.illuwa.coupon.controller;

import com.nhnacademy.illuwa.config.handler.CategoryControllerAdvice;
import com.nhnacademy.illuwa.coupon.dto.MemberCouponResponse;
import com.nhnacademy.illuwa.coupon.service.MemberCouponService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = MyCouponController.class,
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {CategoryControllerAdvice.class})})
@AutoConfigureMockMvc(addFilters = false)
class MyCouponControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberCouponService memberCouponService;

    @Test
    @DisplayName("사용자 소유 쿠폰 조회 (=내 쿠폰함)")
    void myCouponTest() throws Exception {
        // 미사용 쿠폰
        MemberCouponResponse unusedCoupon = new MemberCouponResponse(
                1L,
                "테스트이름",
                "test@test.com"
                , "TestCoupon1"
                , 1L
                , "TEST_CODE1"
                , false
                , null
                , LocalDate.now()
                , LocalDate.now().plusMonths(1)
        );

        // 사용 쿠폰
        MemberCouponResponse usedCoupon = new MemberCouponResponse(
                2L,
                "테스트이름"
                , "test@test.com"
                , "TestCoupon2"
                , 2L,
                "TEST_CODE2"
                , true
                , LocalDate.now()
                , LocalDate.now()
                , LocalDate.now().plusMonths(1)
        );

        when(memberCouponService.getAllMemberCoupons()).thenReturn(List.of(unusedCoupon, usedCoupon));

        mockMvc.perform(get("/mycoupon"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("availableCoupons", hasSize(1)))
                .andExpect(model().attribute("availableCoupons", Matchers.contains(unusedCoupon)))
                .andExpect(model().attribute("usedCoupons", hasSize(1)))
                .andExpect(model().attribute("usedCoupons", Matchers.contains(usedCoupon)))
                .andExpect(model().attribute("activeMenu", "mycoupon"))
                .andExpect(view().name("user/mycoupon"));

        verify(memberCouponService, times(1)).getAllMemberCoupons();
    }
}

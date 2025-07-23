package com.nhnacademy.illuwa.admin.controller;

import com.nhnacademy.illuwa.config.handler.CategoryControllerAdvice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = AdminHomeController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {CategoryControllerAdvice.class})
        })
@AutoConfigureMockMvc(addFilters = false)
public class AdminHomeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @DisplayName("관리자 홈 페이지 접근 시 admin/admin_home 뷰를 반환한다")
    @Test
    void test_homePage() throws Exception {
        mockMvc.perform(get("/admin/home"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/admin_home"));
    }
}

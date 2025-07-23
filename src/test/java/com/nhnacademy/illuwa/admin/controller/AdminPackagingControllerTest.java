package com.nhnacademy.illuwa.admin.controller;

import com.nhnacademy.illuwa.config.handler.CategoryControllerAdvice;
import com.nhnacademy.illuwa.order.dto.command.create.PackagingRequest;
import com.nhnacademy.illuwa.order.dto.query.info.PackagingResponse;
import com.nhnacademy.illuwa.order.service.PackagingService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AdminPackagingController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {CategoryControllerAdvice.class})
        })
@AutoConfigureMockMvc(addFilters = false)
public class AdminPackagingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PackagingService packagingService;

    @DisplayName("포장 옵션 목록 페이지 조회")
    @Test
    void test_packagingPage() throws Exception {
        PackagingResponse option1 = new PackagingResponse();
        PackagingResponse option2 = new PackagingResponse();

        when(packagingService.getAllPackagingOptions())
                .thenReturn(List.of(option1, option2));

        mockMvc.perform(get("/admin/packaging"))
                .andExpect(status().isOk())
                .andExpect(view().name("order/packaging_list"))
                .andExpect(model().attributeExists("packaging"))
                .andExpect(model().attributeExists("packagingRequestDto"));

        verify(packagingService, times(1)).getAllPackagingOptions();
    }

    @DisplayName("포장 옵션 등록 POST")
    @Test
    void test_registerPackagingSubmit() throws Exception {
        mockMvc.perform(post("/admin/packaging")
                        .param("packagingName", "테스트포장")
                        .param("packagingPrice", "2000"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/packaging"));

        ArgumentCaptor<PackagingRequest> captor = ArgumentCaptor.forClass(PackagingRequest.class);
        verify(packagingService).createPackaging(captor.capture());

        PackagingRequest captured = captor.getValue();
        assertThat(captured.getPackagingName()).isEqualTo("테스트포장");
        assertThat(captured.getPackagingPrice()).isEqualByComparingTo("2000");
    }
}

package com.nhnacademy.illuwa.admin.controller;

import com.nhnacademy.illuwa.category.client.CategoryServiceClient;
import com.nhnacademy.illuwa.category.dto.CategoryCreateRequest;
import com.nhnacademy.illuwa.category.dto.CategoryFlatResponse;
import com.nhnacademy.illuwa.config.handler.CategoryControllerAdvice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AdminCategoryController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {CategoryControllerAdvice.class})
        })
@AutoConfigureMockMvc(addFilters = false)
public class AdminCategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ClientRegistrationRepository clientRegistrationRepository;

    @MockBean
    CategoryServiceClient categoryServiceClient;

    @Test
    @DisplayName("카테고리 관리 페이지 테스트")
    void test_categoryManagePage() throws Exception {
        List<CategoryFlatResponse> categoryList = List.of(
                new CategoryFlatResponse(1L, 0L, null, "IT", 0),
                new CategoryFlatResponse(2L, 1L, "IT", "Programming", 1)
        );

        Page<CategoryFlatResponse> categoryPage = new PageImpl<>(categoryList);
        when(categoryServiceClient.getFlatCategoriesPaged(anyInt(), anyInt(), anyString()))
                .thenReturn(categoryPage);

        mockMvc.perform(get("/admin/categories")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "id,asc"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/category/manage"))
                .andExpect(model().attributeExists("categoryPage"))
                .andExpect(model().attributeExists("allCategories"))
                .andExpect(model().attributeExists("newCategory"));

        // drop-down용 조회 빈도까지 검증
        verify(categoryServiceClient, times(2)).getFlatCategoriesPaged(anyInt(), anyInt(), anyString());
    }

    @Test
    @DisplayName("카테고리 등록 테스트")
    void test_createCategory() throws Exception {
        // 폼 파라미터와 DTO를 일치시켜서 제대로 전달되는지 체크
        mockMvc.perform(post("/admin/categories")
                        .param("CategoryName", "경제경영")
                        .param("parentId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/categories"));

        ArgumentCaptor<CategoryCreateRequest> captor = ArgumentCaptor.forClass(CategoryCreateRequest.class);
        verify(categoryServiceClient, times(1)).createCategory(captor.capture());

        CategoryCreateRequest req = captor.getValue();
        assertThat(req.getCategoryName()).isEqualTo("경제경영");
        assertThat(req.getParentId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("카테고리 삭제 테스트")
    void test_deleteCategory() throws Exception {
        mockMvc.perform(post("/admin/categories/5/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/categories"));

        verify(categoryServiceClient, times(1)).deleteCategory(5L);
    }
}

package com.nhnacademy.illuwa.admin.controller;

import com.nhnacademy.illuwa.common.dto.PageResponse;
import com.nhnacademy.illuwa.config.handler.CategoryControllerAdvice;
import com.nhnacademy.illuwa.tag.client.TagServiceClient;
import com.nhnacademy.illuwa.tag.dto.TagCreateRequest;
import com.nhnacademy.illuwa.tag.dto.TagResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AdminTagController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {CategoryControllerAdvice.class})
        })
@AutoConfigureMockMvc(addFilters = false)
public class AdminTagControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TagServiceClient tagServiceClient;

    @DisplayName("태그 관리 페이지 조회 테스트")
    @Test
    void test_tagManagePage() throws Exception {
        TagResponse tag1 = new TagResponse(1L, "tagA");
        TagResponse tag2 = new TagResponse(2L, "tagB");
        List<TagResponse> tags = List.of(tag1, tag2);

        PageResponse<TagResponse> tagPage = new PageResponse<>(
                tags,
                1, // totalPages
                2L, // totalElements
                0,
                10,
                true,
                true);

        when(tagServiceClient.getAllTags(any(Pageable.class))).thenReturn(tagPage);

        mockMvc.perform(get("/admin/tags")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "id,asc"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/tag/manage"))
                .andExpect(model().attributeExists("tagPage"))
                .andExpect(model().attributeExists("newTag"))
                .andExpect(model().attributeExists("sort"));

        verify(tagServiceClient).getAllTags(any(Pageable.class));
    }

    @DisplayName("태그 생성 POST 요청 테스트")
    @Test
    void test_createTag() throws Exception {
        mockMvc.perform(post("/admin/tags")
                        .param("name", "newTag"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/tags"));

        ArgumentCaptor<TagCreateRequest> captor = ArgumentCaptor.forClass(TagCreateRequest.class);
        verify(tagServiceClient).createTag(captor.capture());

        assertThat(captor.getValue().getName()).isEqualTo("newTag");
    }

    @DisplayName("태그 삭제 POST 요청 테스트")
    @Test
    void test_deleteTag() throws Exception {
        mockMvc.perform(post("/admin/tags/5/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/tags"));

        verify(tagServiceClient).deleteTag(5L);
    }
}

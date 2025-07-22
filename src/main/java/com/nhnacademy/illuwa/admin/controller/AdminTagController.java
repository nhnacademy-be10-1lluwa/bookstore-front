package com.nhnacademy.illuwa.admin.controller;

import com.nhnacademy.illuwa.common.dto.PageResponse;
import com.nhnacademy.illuwa.tag.client.TagServiceClient;
import com.nhnacademy.illuwa.tag.dto.TagCreateRequest;
import com.nhnacademy.illuwa.tag.dto.TagResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/tags")
public class AdminTagController {

    private final TagServiceClient tagServiceClient;

    //태그 관리 페이지
    @GetMapping()
    public String tagManagePage(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            Model model
    ) {
        PageResponse<TagResponse> tagPage = tagServiceClient.getAllTags(pageable);
        model.addAttribute("tagPage", tagPage);
        model.addAttribute("newTag", new TagCreateRequest());
        model.addAttribute("sort", pageable.getSort().toString().replace(": ", ","));
        return "admin/tag/manage";
    }

    @PostMapping
    public String createTag(@ModelAttribute TagCreateRequest request) {
        tagServiceClient.createTag(request);
        return "redirect:/admin/tags";
    }

    @PostMapping("/{id}/delete")
    public String deleteTag(@PathVariable Long id) {
        tagServiceClient.deleteTag(id);
        return "redirect:/admin/tags";
    }
}



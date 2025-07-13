package com.nhnacademy.illuwa.admin.controller;
import com.nhnacademy.illuwa.tag.dto.PageResponse;
import com.nhnacademy.illuwa.tag.client.TagServiceClient;
import com.nhnacademy.illuwa.tag.dto.TagCreateRequest;
import com.nhnacademy.illuwa.tag.dto.TagResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/tag")
public class AdminTagController {

    private final TagServiceClient tagServiceClient;

    @GetMapping("/manage")
    public String tagManagePage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String sort,
            Model model
    ) {
        PageResponse<TagResponse> tagPage = tagServiceClient.getAllTags(page, size, sort);
        model.addAttribute("tagPage", tagPage);
        model.addAttribute("newTag", new TagCreateRequest());
        return "admin/tag/manage";
    }

    @PostMapping("/create")
    public String createTag(@ModelAttribute TagCreateRequest request) {
        tagServiceClient.createTag(request);
        return "redirect:/admin/tag/manage";
    }

    @PostMapping("/delete/{id}")
    public String deleteTag(@PathVariable Long id) {
        tagServiceClient.deleteTag(id);
        return "redirect:/admin/tag/manage";
    }
}



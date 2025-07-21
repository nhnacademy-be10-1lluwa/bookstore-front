package com.nhnacademy.illuwa.admin.controller;

import com.nhnacademy.illuwa.category.dto.CategoryFlatResponse;
import com.nhnacademy.illuwa.category.client.CategoryServiceClient;
import com.nhnacademy.illuwa.category.dto.CategoryCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/categories")
public class AdminCategoryController {

    private final CategoryServiceClient categoryServiceClient;

    // 카테고리 등록
    @PostMapping
    public String createCategory(@ModelAttribute CategoryCreateRequest request) {
            categoryServiceClient.createCategory(request);
        return "redirect:/admin/categories";
    }

    @PostMapping("/{id}/delete")
    public String deleteCategory(@PathVariable Long id) {
            categoryServiceClient.deleteCategory(id);
        return "redirect:/admin/categories";
    }

    // 카테고리 관리 페이지
    @GetMapping
    public String categoryManagePage(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size,
                                     @RequestParam(defaultValue = "id,asc") String sort,
                                     Model model) {
        // 페이징된 목록
        Page<CategoryFlatResponse> categoryPage = categoryServiceClient.getFlatCategoriesPaged(page, size, sort);

        // 드롭다운
        Page<CategoryFlatResponse> allPage = categoryServiceClient.getFlatCategoriesPaged(0, 100, sort);
        List<CategoryFlatResponse> allCategories = allPage.getContent();

        model.addAttribute("categoryPage", categoryPage);
        model.addAttribute("allCategories", allCategories);
        model.addAttribute("newCategory", new CategoryCreateRequest());

        return "admin/category/manage";
    }
}
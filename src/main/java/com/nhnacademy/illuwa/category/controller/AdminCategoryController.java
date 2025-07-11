package com.nhnacademy.illuwa.category.controller;

import com.nhnacademy.illuwa.book.dto.CategoryResponse;
import com.nhnacademy.illuwa.category.client.CategoryServiceClient;
import com.nhnacademy.illuwa.category.dto.CategoryCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/category")
public class AdminCategoryController {

    private final CategoryServiceClient categoryServiceClient;

    @GetMapping("/manage")
    public String showCategoryManagementPage(Model model) {
        List<CategoryResponse> categoryTree = categoryServiceClient.getCategoryTree();
        model.addAttribute("categoryTree", categoryTree);

        model.addAttribute("newCategory", new CategoryCreateRequest());
        return "admin/category/manage";
    }

    @PostMapping("/create")
    public String createCategory(@ModelAttribute CategoryCreateRequest request) {
            categoryServiceClient.createCategory(request);
        return "redirect:/admin/category/manage";
    }

    @PostMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
            categoryServiceClient.deleteCategory(id);
        return "redirect:/admin/category/manage";
    }
}
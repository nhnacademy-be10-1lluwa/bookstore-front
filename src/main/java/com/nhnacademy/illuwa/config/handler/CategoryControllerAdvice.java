package com.nhnacademy.illuwa.config.handler;

import com.nhnacademy.illuwa.category.dto.CategoryResponse;
import com.nhnacademy.illuwa.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice
@RequiredArgsConstructor
public class CategoryControllerAdvice {

    private final CategoryService categoryService;

    @ModelAttribute("categories")
    public List<CategoryResponse> populateCategories() {
        return categoryService.getAllCategories();
    }
}
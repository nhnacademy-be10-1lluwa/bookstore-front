package com.nhnacademy.illuwa.category.controller;

import com.nhnacademy.illuwa.book.client.ProductServiceClient;
import com.nhnacademy.illuwa.category.dto.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final ProductServiceClient productServiceClient;

    @GetMapping("/tree")
    public List<CategoryResponse> getCategoryTree() {
        List<CategoryResponse> categoryTree = productServiceClient.getCategoryTree("tree");
        return categoryTree;
    }
}

package com.nhnacademy.illuwa.category.service;

import com.nhnacademy.illuwa.category.client.CategoryServiceClient;
import com.nhnacademy.illuwa.category.dto.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryServiceClient categoryClient;

    public List<CategoryResponse> getAllCategories() {
        return categoryClient.getAllCategories();
    }
}
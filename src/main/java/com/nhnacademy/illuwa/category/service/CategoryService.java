package com.nhnacademy.illuwa.category.service;

import com.nhnacademy.illuwa.category.client.CategoryServiceClient;
import com.nhnacademy.illuwa.category.dto.CategoryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {

    private final CategoryServiceClient categoryClient;

    @Cacheable(value = "categories")
    public List<CategoryResponse> getAllCategories() {
        log.info("현재 캐시에 저장된 카테고리 목록 존재 X, 가져오는중...");
        return categoryClient.getAllCategories();
    }
}
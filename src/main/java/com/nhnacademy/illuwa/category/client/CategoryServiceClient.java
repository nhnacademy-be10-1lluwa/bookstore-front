package com.nhnacademy.illuwa.category.client;

import com.nhnacademy.illuwa.book.dto.CategoryResponse;
import com.nhnacademy.illuwa.category.dto.CategoryCreateRequest;
import com.nhnacademy.illuwa.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "product-service", url = "${api.base-url}", contextId = "categoryClient", configuration = FeignClientConfig.class)
public interface CategoryServiceClient {

    @GetMapping("/api/categories/tree")
    List<CategoryResponse> getCategoryTree();

    @PostMapping("/api/categories")
    void createCategory(@RequestBody CategoryCreateRequest request);

    @DeleteMapping("/api/categories/{id}")
    void deleteCategory(@PathVariable("id") Long id);
}
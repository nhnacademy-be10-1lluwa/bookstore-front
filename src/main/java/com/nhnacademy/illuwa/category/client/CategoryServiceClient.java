package com.nhnacademy.illuwa.category.client;

import com.nhnacademy.illuwa.category.dto.CategoryFlatResponse;
import com.nhnacademy.illuwa.category.dto.CategoryResponse;
import com.nhnacademy.illuwa.category.dto.CategoryCreateRequest;
import com.nhnacademy.illuwa.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "product-service", url = "${api.base-url}", contextId = "categoryClient", configuration = FeignClientConfig.class)
public interface CategoryServiceClient {

    @GetMapping("/api/categories/tree")
    List<CategoryResponse> getCategoryTree();

    @GetMapping("/api/public/categories")
    List<CategoryResponse> getAllCategories();

    // 카테고리 생성
    @PostMapping("/api/categories")
    void createCategory(@RequestBody CategoryCreateRequest request);

    @DeleteMapping("/api/categories/{id}")
    void deleteCategory(@PathVariable("id") Long id);

    // 카테고리 pagination
    @GetMapping("/api/categories/page")
    Page<CategoryFlatResponse> getFlatCategories(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sort") String sort
    );

    @GetMapping("/api/categories/flat_paged")
    Page<CategoryFlatResponse> getFlatCategoriesPaged(@RequestParam("page") int page,
                                                      @RequestParam("size") int size,
                                                      @RequestParam("sort") String sort);


}
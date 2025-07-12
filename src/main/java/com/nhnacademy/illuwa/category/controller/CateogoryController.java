package com.nhnacademy.illuwa.category.controller;

import com.nhnacademy.illuwa.book.client.ProductServiceClient;
import com.nhnacademy.illuwa.category.dto.CategoryResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CateogoryController {
    private final ProductServiceClient productServiceClient;

    public CateogoryController(ProductServiceClient productServiceClient, ProductServiceClient productServiceClient1){
        this.productServiceClient = productServiceClient1;
    }

    @GetMapping("/tree")
    public List<CategoryResponse> getCategoryTree() {

        List<CategoryResponse> categoryTree = productServiceClient.getCategoryTree();
        return categoryTree;
    }


}

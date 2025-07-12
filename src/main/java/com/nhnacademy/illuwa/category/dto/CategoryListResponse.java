package com.nhnacademy.illuwa.category.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryListResponse {
    private Long id;
    private Long parentId;
    private String parentName;
    private String categoryName;
    private int depth;
}
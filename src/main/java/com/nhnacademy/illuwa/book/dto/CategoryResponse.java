package com.nhnacademy.illuwa.book.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {
    private Long id;
    private Long parentId;
    private String categoryName;
    private List<CategoryResponse> children = new ArrayList<>();
}
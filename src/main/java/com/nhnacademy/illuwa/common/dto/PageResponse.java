package com.nhnacademy.illuwa.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public record PageResponse<T>(
        List<T> content,
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean first,
        boolean last)
{}

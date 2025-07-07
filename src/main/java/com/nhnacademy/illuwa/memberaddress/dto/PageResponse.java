package com.nhnacademy.illuwa.memberaddress.dto;

import lombok.Data;

import java.util.List;

@Data
public class PageResponse<T> {
    private List<T> content;
    private int totalPages;
    private int totalElements;
    private int number;
    private boolean last;
    private boolean first;
}


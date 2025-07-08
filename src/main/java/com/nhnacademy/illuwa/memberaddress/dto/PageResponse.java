package com.nhnacademy.illuwa.memberaddress.dto;

import lombok.Data;

import java.util.List;

@Data
public class PageResponse<T> {
    private List<T> content;
    private int totalPages;
    private int totalElements;
    private int number; // 현재 페이지 번호 (0-based)
    private int size;   // 한 페이지에 들어가는 아이템 수
    private boolean last;
    private boolean first;
}


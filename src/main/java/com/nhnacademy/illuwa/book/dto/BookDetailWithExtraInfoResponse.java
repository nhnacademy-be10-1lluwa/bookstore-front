package com.nhnacademy.illuwa.book.dto;

import groovy.transform.builder.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDetailWithExtraInfoResponse {
    private Long id;
    private String title;
    private String contents;
    private String description;
    private String author;
    private String publisher;
    private String publishedDate;  // String 타입으로 날짜
    private String isbn;
    private BigDecimal regularPrice;
    private BigDecimal salePrice;
    private boolean giftwrap;
    private Integer count;
    private String imgUrl;
    private String status;
    private Long categoryId;
    private Long level1;
    private Long level2;
    private String level1Name;
    private String level2Name;
    private String categoryName;
}
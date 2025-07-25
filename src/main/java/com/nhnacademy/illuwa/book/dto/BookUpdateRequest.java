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
public class BookUpdateRequest {
    private Long id;

    private String title;
    private String author;
    private String publisher;
    private String pubDate;
    private String isbn;

    private BigDecimal regularPrice;
    private BigDecimal salePrice;

    private String description;
    private String contents;

    private String cover;

    private Integer count;

    private String status;

    private Boolean giftwrap;

    private Long level1;
    private Long level2;
    private Long categoryId;
}
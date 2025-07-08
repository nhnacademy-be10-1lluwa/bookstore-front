package com.nhnacademy.illuwa.book.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AladinBookRegisterRequest {
    private String title;
    private String author;
    private String publisher;
    private String description;
    private String pubDate;
    private String isbn;
    private int priceStandard;
    private int priceSales;
    private String cover;
    private String categoryName;
    private Integer count;
}

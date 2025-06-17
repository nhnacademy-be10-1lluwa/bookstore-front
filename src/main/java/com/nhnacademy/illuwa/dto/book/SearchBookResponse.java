package com.nhnacademy.illuwa.dto.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchBookResponse {
    private String title;
    private String contents;
    private String description;
    private String category;
    private String author;
    private String publisher;
    private String publishedAt;
    private String isbn;
    private BigDecimal priceOriginal;
    private BigDecimal priceSale;
    private boolean isGiftWrap;
    private String imageUrl;
}

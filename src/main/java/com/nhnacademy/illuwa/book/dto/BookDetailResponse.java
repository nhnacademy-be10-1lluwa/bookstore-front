package com.nhnacademy.illuwa.book.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDetailResponse {
    private Long id;
    private String title;
    private String contents;
    private String description;
    private String author;
    private String publisher;
    private String publishedDate;
    private String isbn;
    private BigDecimal regularPrice;
    private BigDecimal salePrice;
    private boolean isGiftWrap;
    private String imgUrl;
}

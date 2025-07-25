package com.nhnacademy.illuwa.book.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BestSellerResponse {
    private String title;
    private String author;
    private String publisher;
    private String description;
    private String isbn;
    private int priceSales;
    private int priceStandard;
    private String cover;
}

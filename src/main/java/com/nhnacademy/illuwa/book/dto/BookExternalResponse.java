package com.nhnacademy.illuwa.book.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookExternalResponse {

    private String title;
    private String description;
    private String author;
    private String publisher;
    private LocalDate pubDate;
    private String isbn;
    private Integer priceStandard;
    private Integer priceSales;
    private String cover;
    private String categoryName;

}
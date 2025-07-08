package com.nhnacademy.illuwa.book.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookRegisterRequest {
    private String title;
    private String author;
    private String publisher;
    private String contents;
    private LocalDate pubDate;
    private String isbn;
    private int regularPrice;
    private int salePrice;
    private String description;
    private MultipartFile imageFile;
    private Integer count;
    private Long categoryId;
}


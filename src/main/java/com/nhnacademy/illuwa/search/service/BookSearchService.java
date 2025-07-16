package com.nhnacademy.illuwa.search.service;

import com.nhnacademy.illuwa.search.client.BookSearchClient;
import com.nhnacademy.illuwa.search.dto.BookDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;



@Service
@RequiredArgsConstructor
public class BookSearchService {
    private final BookSearchClient bookSearchClient;

    public Page<BookDocument> searchBooks(String keyword, Pageable pageable) {
        String sortParam = pageable.getSort().stream()
                .map(order -> order.getProperty() + "," + order.getDirection().name().toLowerCase())
                .findFirst()
                .orElse("id,asc"); // 기본정렬

        return bookSearchClient.searchBooks(keyword, pageable.getPageNumber(), pageable.getPageSize(), sortParam);
    }

    public Page<BookDocument> searchBooksByCategory(String category, Pageable pageable) {
        String sortParam = pageable.getSort()
                .stream()
                .map(order -> order.getProperty() + "," + order.getDirection().name().toLowerCase())
                .findFirst()
                .orElse("id,asc");

        return bookSearchClient.searchBooksByCategory(category, pageable.getPageNumber(), pageable.getPageSize(), sortParam);
    }

}
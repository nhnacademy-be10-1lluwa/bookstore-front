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

        Page<BookDocument> resultPage = bookSearchClient.searchBooks(keyword, pageable.getPageNumber(), pageable.getPageSize(), sortParam);
        resultPage.getContent().forEach(book -> {
            book.setThumbnailUrl(convertMinioUrl(book.getThumbnailUrl()));
        });

        return resultPage;
    }

    public Page<BookDocument> searchBooksByCategory(String category, Pageable pageable) {
        String sortParam = pageable.getSort()
                .stream()
                .map(order -> order.getProperty() + "," + order.getDirection().name().toLowerCase())
                .findFirst()
                .orElse("id,asc");

        Page<BookDocument> resultPage = bookSearchClient.searchBooks(category, pageable.getPageNumber(), pageable.getPageSize(), sortParam);

        resultPage.getContent().forEach(book -> {
            book.setThumbnailUrl(convertMinioUrl(book.getThumbnailUrl()));
        });

        return resultPage;
    }

    // Nginx 경로 우회 메서드(http -> https)
    private String convertMinioUrl(String originalUrl) {
        if (originalUrl == null) {
            return null;
        }
        return originalUrl.replace(
                "http://storage.java21.net:8000/",
                "https://book1lluwa.store/minio/"
        );
    }

}
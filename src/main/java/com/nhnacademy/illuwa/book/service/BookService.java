package com.nhnacademy.illuwa.book.service;

import com.nhnacademy.illuwa.book.client.ProductServiceClient;
import com.nhnacademy.illuwa.book.dto.BestSellerResponse;
import com.nhnacademy.illuwa.book.dto.BookDetailResponse;
import com.nhnacademy.illuwa.category.dto.CategoryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class BookService {

    private final ProductServiceClient productServiceClient;

    public BookDetailResponse findBookByIsbn(String isbn){
        return productServiceClient.findBookByIsbn(isbn);
    }

    public BookDetailResponse findBookById(Long id){

        BookDetailResponse response = productServiceClient.findBookById(id);
        response.setImageUrls(response.getImageUrls().stream().map(this::convertMinioUrl).toList());

        return response;
    }

    public List<BookDetailResponse> getAllBooks() {
        return productServiceClient.getRegisteredBook();
    }

    public Page<BookDetailResponse> getPagedBooks(int page, int size, String sort) {
        return productServiceClient.getRegisteredBookPaged(page, size, sort);
    }

    public List<CategoryResponse> getAllCategory() {
        return productServiceClient.getAllCategories();
    }

    @Cacheable(value = "bestSellers")
    public List<BestSellerResponse> getBestSellers() {
        log.info("현재 캐시에 인기도서 목록 존재 X, 가져오는중...");
        return productServiceClient.getBestSeller();
    }

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

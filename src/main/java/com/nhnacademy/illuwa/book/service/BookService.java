package com.nhnacademy.illuwa.book.service;

import com.nhnacademy.illuwa.book.client.ProductServiceClient;
import com.nhnacademy.illuwa.book.dto.BestSellerResponse;
import com.nhnacademy.illuwa.book.dto.BookDetailResponse;
import com.nhnacademy.illuwa.book.dto.SearchBookResponse;
import com.nhnacademy.illuwa.category.dto.CategoryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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
        return productServiceClient.findBookById(id);
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
        log.info("cache에 인기도서 목록 존재X");
        return productServiceClient.getBestSeller();
    }


}

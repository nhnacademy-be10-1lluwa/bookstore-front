package com.nhnacademy.illuwa.book.service;

import com.nhnacademy.illuwa.book.client.ProductServiceClient;
import com.nhnacademy.illuwa.book.dto.BookDetailResponse;
import com.nhnacademy.illuwa.book.dto.SearchBookResponse;
import com.nhnacademy.illuwa.category.dto.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookService {

    private final ProductServiceClient productServiceClient;

    public BookDetailResponse findBookByIsbn(String isbn){
        return productServiceClient.findBookByIsbn(isbn);
    }

    public List<SearchBookResponse> bookList() {
        return productServiceClient.findBooks();
    }

    public BookDetailResponse bookDetail(@RequestParam String keyword) {
        return productServiceClient.getBookDetail(keyword);
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
}

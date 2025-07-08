package com.nhnacademy.illuwa.book.service;

import com.nhnacademy.illuwa.book.client.ProductServiceClient;
import com.nhnacademy.illuwa.book.dto.BookDetailResponse;
import com.nhnacademy.illuwa.book.dto.SearchBookResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookService {

    private final ProductServiceClient productServiceClient;

    public List<SearchBookResponse> bookList() {
        return productServiceClient.findBooks();
    }

    public BookDetailResponse bookDetail(@RequestParam String keyword) {
        return productServiceClient.getBookDetail(keyword);
    }

    public List<BookDetailResponse> getAllBooks() {
        return productServiceClient.registeredBook();
    }
}

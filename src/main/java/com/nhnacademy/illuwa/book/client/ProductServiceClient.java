package com.nhnacademy.illuwa.book.client;

import com.nhnacademy.illuwa.book.dto.BookDetailResponse;
import com.nhnacademy.illuwa.book.dto.SearchBookResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "product-service", url = "${api.base-url}")
public interface ProductServiceClient {
    @GetMapping("/books/{id}")
    BookDetailResponse getBookDetail(@PathVariable String id);

    @GetMapping("/books")
    List<SearchBookResponse> findBooks();
}

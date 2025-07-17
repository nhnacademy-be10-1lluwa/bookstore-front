package com.nhnacademy.illuwa.search.client;

import com.nhnacademy.illuwa.search.dto.BookDocument;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "product-service", url = "${api.base-url}")
public interface BookSearchClient {
    @GetMapping("/api/books/search")
    Page<BookDocument> searchBooks(@RequestParam String keyword, @RequestParam int page, @RequestParam int size, @RequestParam String sort);

    @GetMapping("/api/books/search/category")
    Page<BookDocument> searchBooksByCategory(@RequestParam String category, @RequestParam int page, @RequestParam int size, @RequestParam String sort);
}

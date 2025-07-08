package com.nhnacademy.illuwa.book.client;

import com.nhnacademy.illuwa.book.dto.BestSellerResponse;
import com.nhnacademy.illuwa.book.dto.BookDetailResponse;
import com.nhnacademy.illuwa.book.dto.SearchBookResponse;
import com.nhnacademy.illuwa.cart.dto.CartResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "product-service", url = "${api.base-url}", contextId = "bookClient")
public interface ProductServiceClient {

    @GetMapping("/books/{id}")
    BookDetailResponse getBookDetail(@PathVariable String id);

    @GetMapping("/books/search")
    List<SearchBookResponse> findBooks();

    @GetMapping("/books/bestseller")
    List<BestSellerResponse> getBestSeller();


    @GetMapping("/books")
    List<BookDetailResponse> registeredBook();
}

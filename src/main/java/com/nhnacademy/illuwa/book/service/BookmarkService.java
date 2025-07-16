package com.nhnacademy.illuwa.book.service;

import com.nhnacademy.illuwa.book.client.ProductServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@RequiredArgsConstructor
public class BookmarkService {
    ProductServiceClient productServiceClient;

    public Boolean toggleBookmark(@RequestParam long bookId) {
        return productServiceClient.toggleBookmark(bookId);
    }
}

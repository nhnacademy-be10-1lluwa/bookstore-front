package com.nhnacademy.illuwa.booklike.client;

import com.nhnacademy.illuwa.book.dto.SimpleBookResponse;
import com.nhnacademy.illuwa.common.dto.PageResponse;
import com.nhnacademy.illuwa.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "product-service", url = "${api.base-url}", contextId = "ProductClientForBookLikes", configuration = FeignClientConfig.class)
public interface BookLikeServiceClient {

    @PostMapping("/api/book-likes")
    void toggleBookLikes(@RequestParam long bookId);

    @GetMapping("/api/book-likes/check")
    Boolean isLikedByMe(@RequestParam Long bookId);

    @GetMapping("/api/book-likes/list")
    PageResponse<SimpleBookResponse> getLikedBooksByMember(@RequestParam("page") int page,
                                                           @RequestParam("size") int size);
}
